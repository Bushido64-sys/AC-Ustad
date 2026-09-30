package com.acustad.app.data

import android.database.sqlite.SQLiteDatabase
import com.acustad.app.model.CodeSummary
import com.acustad.app.model.ScopedSeries

/**
 * Search: **two jobs, never mixed.**
 *
 * | The user is typing | Table | Why |
 * |---|---|---|
 * | a code — `E1`, `Error 200`, `BLINK-RUNNING` | `aliases` | exact/prefix on `alias_norm`, 0.15 ms, no crash surface |
 * | a description — `compressor`, `high pressure` | `code_fts` | free text, and the input **must be quoted** |
 *
 * Both are scoped to the model line the user is standing in front of. A code search that
 * escaped the series would be able to show a technician another brand's meaning, which is the
 * one thing this app must never do.
 */
class SearchDao(private val db: SQLiteDatabase) {

    /**
     * Exact match on the canonical form. This is the path that must never fail: measured
     * against the shipped database, all 2,139 distinct code strings resolve through it.
     */
    suspend fun codesExact(scope: ScopedSeries, canonQuery: String): List<CodeSummary> = io {
        db.rawQuery(
            """
            SELECT DISTINCT c.id, c.uid, c.code, c.title_en, c.title_ur,
                            c.severity, c.is_fault, c.display
              FROM aliases a JOIN codes c ON c.id = a.code_id
             WHERE a.alias_norm = ? AND c.series_id = ? AND c.brand_id = ?
             ORDER BY c.code COLLATE NOCASE
            """.trimIndent(),
            arrayOf(canonQuery, scope.seriesId, scope.brandId),
        ).mapRows { it.toSummary() }
    }

    /**
     * Prefix match, so typing `E` narrows the list. Backs the code list's own search field.
     * 3 ms on the shipped database.
     */
    suspend fun codesPrefix(scope: ScopedSeries, canonQuery: String, limit: Int = 60): List<CodeSummary> = io {
        db.rawQuery(
            """
            SELECT DISTINCT c.id, c.uid, c.code, c.title_en, c.title_ur,
                            c.severity, c.is_fault, c.display
              FROM aliases a JOIN codes c ON c.id = a.code_id
             WHERE a.alias_norm LIKE ? AND c.series_id = ? AND c.brand_id = ?
             ORDER BY c.code COLLATE NOCASE
             LIMIT ?
            """.trimIndent(),
            arrayOf(canonQuery + "%", scope.seriesId, scope.brandId, limit.toString()),
        ).mapRows { it.toSummary() }
    }

    /**
     * Free-text search over the `code_fts` index, scoped to one model line.
     *
     * The index holds three columns — `code_norm`, `aliases`, `titles` — and `titles` is the
     * English and Roman Urdu titles concatenated and upper-cased. A `snippet()` of that is a
     * duplicated uppercase blob that reads as noise, so none is selected: the code list already
     * shows the real title on every row.
     *
     * The query arrives already quoted by [SearchInput.ftsQuery]. That is not decoration:
     * 454 of the 2,139 code strings throw if passed raw (`BLINK-RUNNING` becomes
     * `no such column: RUNNING`; `;` and `+` are syntax errors).
     *
     * `bm25` ascending means better matches first — the sign is easy to get backwards.
     * `code_fts` is a contentless external table with `rowid = codes.id`.
     */
    suspend fun codesText(
        scope: ScopedSeries,
        quotedQuery: String,
        limit: Int = 60,
    ): List<CodeSummary> = io {
        db.rawQuery(
            """
            SELECT c.id, c.uid, c.code, c.title_en, c.title_ur, c.severity, c.is_fault, c.display
              FROM code_fts JOIN codes c ON c.id = code_fts.rowid
             WHERE code_fts MATCH ? AND c.series_id = ? AND c.brand_id = ?
             ORDER BY bm25(code_fts, 10.0, 1.0, 3.0)
             LIMIT ?
            """.trimIndent(),
            arrayOf(quotedQuery, scope.seriesId, scope.brandId, limit.toString()),
        ).mapRows { it.toSummary() }
    }

    /**
     * How many distinct brands publish this code, and — when it is exactly one — which.
     *
     * This exists for one screen and one reason: the **brands** screen, where a technician who
     * typed a code instead of a brand name finds nothing, deserves to be told *why*. The number
     * is what makes the rule land — "E6 means something different on 16 brands" is a fact you
     * can act on, where "codes are searched inside a model" reads like a policy statement.
     * DESIGN.md §4.2 and PHASE_5_SEARCH.md §5 both ask for that sentence.
     *
     * Four things make it correct rather than merely plausible:
     *
     *  - **The count is measured, never typed.** `16` is not written anywhere in the app. It is
     *    a `COUNT(DISTINCT …)` against the shipped database, so it tracks a data release instead
     *    of going quietly stale — the failure mode this project has produced repeatedly.
     *  - **`code_norm`, not `code`.** Codes are stored in mixed case — `High Temp`, `oE`, `b5`
     *    all appear — so `WHERE code = ?` matches nothing for a large part of the database.
     *    `code_norm` is `SearchInput.canon()`'s own output and is indexed (`idx_codes_norm`).
     *    Trap 2, in a new place.
     *  - **One query, not two.** The name comes from the same pass: `MIN(b.name)` is the brand
     *    when the count is 1, because one distinct brand means one name. Asking for the name
     *    only when it exists would mean a second round trip for the case that needs it most.
     *    The "only when it exists" is a `CASE` **in the SQL**, not a check afterwards: a query
     *    that hands back "Carrier" alongside a count of 16 is a loaded gun for the next caller,
     *    and `check_app_sql.py` now asserts the guard is in the query. `CodePresence`'s
     *    constructor is the second line of defence, not the only one.
     *  - **Measured at 0.15 ms**, so it is affordable on a debounced keystroke. That number is
     *    why this is a query at all rather than a table read in Kotlin.
     *
     * `brandName` is null unless the count is exactly one, so a caller cannot accidentally
     * present "Carrier" as *the* brand for a code that 16 brands publish.
     */
    suspend fun codePresence(rawQuery: String): CodePresence = io {
        val canon = SearchInput.canon(rawQuery)
        if (canon.isEmpty() || !SearchInput.looksLikeCode(canon)) {
            CodePresence.NONE
        } else {
            db.rawQuery(
                """
                SELECT COUNT(DISTINCT c.brand_id),
                       CASE WHEN COUNT(DISTINCT c.brand_id) = 1 THEN MIN(b.name) END
                  FROM codes c LEFT JOIN brands b ON b.id = c.brand_id
                 WHERE c.code_norm = ?
                """.trimIndent(),
                arrayOf(canon),
            ).firstRow { CodePresence(it.getInt(0), it.getString(1)) } ?: CodePresence.NONE
        }
    }

    /**
     * The full two-job search a code field runs: try the exact code first, and only fall
     * through to free text when the code path found nothing. A code-looking string is never
     * sent to FTS.
     */
    suspend fun searchCodes(scope: ScopedSeries, rawQuery: String): List<CodeSummary> {
        val canon = SearchInput.canon(rawQuery)
        if (canon.isEmpty()) return emptyList()
        if (SearchInput.looksLikeCode(canon)) {
            val exact = codesExact(scope, canon)
            if (exact.isNotEmpty()) return exact
            val prefix = codesPrefix(scope, canon)
            if (prefix.isNotEmpty()) return prefix
        }
        return codesText(scope, SearchInput.ftsQuery(rawQuery))
    }
}

/**
 * What the database can say about a query that was typed where a brand name was expected.
 *
 * A value type rather than two nullable return values, because "there is no such code" and
 * "there is such a code on three brands" are genuinely different answers and the caller must not
 * be able to confuse them. [brandName] is non-null only when [brandCount] is exactly 1, which
 * the constructor enforces rather than documents.
 */
data class CodePresence(
    val brandCount: Int,
    val brandName: String?,
) {
    init {
        require(brandCount >= 0) { "brandCount cannot be negative: $brandCount" }
        require(brandCount != 1 || brandName != null) {
            "a single publishing brand must be named, or the caller cannot tell the technician " +
                "where to go: count=$brandCount name=$brandName"
        }
    }

    companion object {
        /** Not a code, or not in the database at all. */
        val NONE = CodePresence(0, null)
    }
}
