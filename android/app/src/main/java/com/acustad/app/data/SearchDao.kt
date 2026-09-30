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
 * | a description — `compressor`, `high pressure` | `code_fts`, then the series' own text | free text, and the input **must be quoted** |
 *
 * Both are scoped to the model line the user is standing in front of. A code search that
 * escaped the series would be able to show a technician another brand's meaning, which is the
 * one thing this app must never do.
 *
 * ### Why the description job has two steps
 *
 * `code_fts` holds three columns — `code_norm`, `aliases`, `titles` — and `titles` is only the
 * English and Roman Urdu *titles*. The meanings, causes and fix steps are not in it
 * (`DATA_SCHEMA.md` §2). So the index answers `compressor` when some code in the model is
 * called *Compressor overheating protection*, and answers nothing at all for `air leakage`,
 * which is a way a technician describes a fault and appears in nobody's title.
 *
 * [codesDescription] is the second step: the same words, AND-joined with `LIKE`, against this
 * one model's titles, meanings, notes, causes and fix steps. It runs only when the index came
 * back empty, so nothing that already works is re-ranked or re-queried, and it never runs for
 * a string that is a code somewhere in the knowledge base — a code that this model does not
 * have must still be told "no code matches", not shown a list of codes that merely *mention*
 * it. `PHASE_5_SEARCH.md` §1 promises free text searches "that series' descriptions"; the
 * index alone could not keep that promise.
 *
 * ### And a third step, which loosens the first one
 *
 * When both of those answer nothing — the words together appear nowhere in this model —
 * [SearchInput.ftsQueryAny] asks the *same* index for the words it does hold in titles,
 * OR-joined. Measured on the shipped database no title contains both `air` and `leakage` (0 of
 * 4,418), while *Refrigerant leakage detection* and *Anti-Cold Air Feature On* hold one of
 * them each: a technician who typed both is shown those, scoped exactly like every other step,
 * instead of a bare "no code matches". It runs last so the precise answers always win, and
 * never for a single word, which has nothing to loosen.
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
     * The second half of the description job: the technician's words against the text that
     * describes a fault, inside one model line.
     *
     * ### What it searches, and why it is not `code_fts`
     *
     * `title_en`, `title_ur`, `meaning_en`, `meaning_ur`, `notes_en`, `notes_ur`, and every
     * `causes` and `solutions` row belonging to the code — i.e. everything the detail screen
     * would show them. The index cannot be asked this because it does not hold those columns
     * (`DATA_SCHEMA.md` §2), and rebuilding it would change the shipped database's checksum
     * and the data contract with it. The index stays titles-only; this query picks up exactly
     * what it leaves out.
     *
     * ### Why `LIKE` here is safe, when `MATCH` would not be
     *
     * Two separate reasons, both worth stating:
     *
     *  - **The input cannot contain a wildcard.** Terms come from [SearchInput.descriptionTerms]
     *    and match `[A-Z0-9]+`, so `%` and `_` are separators before they ever reach the
     *    parameter list. No `ESCAPE` clause is needed, and none is a silent hole: a missing
     *    `ESCAPE` on user input would let `100%` mean "everything".
     *  - **There is no FTS parser to crash.** `MATCH` with raw input throws for 454 of the
     *    2,139 code strings. `LIKE` has no grammar at all — it cannot throw, whatever it is
     *    handed.
     *
     * Case is handled by SQLite itself: `LIKE` is case-insensitive for ASCII on both sides, and
     * Roman Urdu in this database is ASCII (`KHARABI`, not `خراب`).
     *
     * ### Scope, and the cost
     *
     * `series_id` and `brand_id` are both bound, in this query and not at the call site —
     * `series_id` alone is not unique (6 values repeat, one 14 times), so a query bound on the
     * series alone would return another brand's codes for those 6. The whole scan is one model
     * line: measured **1.5 ms** on a four-code series and **7.8 ms** on the largest one (106
     * codes, every cause and fix step of each), against the `PHASE_5_SEARCH.md` §6 budget of
     * 50 ms — and it only ever runs after the indexed paths found nothing.
     *
     * Ordered by code string like the other two lookups, not by relevance: this is the third
     * attempt, its rows are by definition ones no index ranked, and a deterministic order beats
     * a ranked one a technician cannot predict.
     *
     * [DESCRIPTION_SQL] below is **read** by `app-pipeline/check_app_sql.py`: the checker
     * extracts that string out of this file rather than keeping a second copy, so the two
     * cannot drift, and runs it in CI — because a JVM unit test cannot reach
     * `android.database.sqlite`.
     */
    suspend fun codesDescription(
        scope: ScopedSeries,
        terms: List<String>,
        limit: Int = 60,
    ): List<CodeSummary> {
        if (terms.isEmpty()) return emptyList()
        val where = List(terms.size) { "d.haystack LIKE ?" }.joinToString(" AND ")
        val sql = DESCRIPTION_SQL.replace(WHERE_HOLDER, where)
        return io {
            db.rawQuery(
                sql,
                arrayOf(scope.seriesId, scope.brandId) +
                    terms.map { "%$it%" } +
                    limit.toString(),
            ).mapRows { it.toSummary() }
        }
    }

    /**
     * Is this canonical query the canonical form of a code that exists somewhere in the
     * knowledge base?
     *
     * The question the description fallback must answer before it speaks, and it is answered by
     * the database rather than by the shape of the string. A digit test would get three of the
     * four obvious cases wrong: `E6` and `Error 200` are codes, `compressor` and `air leakage`
     * are not — but 27% of the 2,139 code strings (`BLINK-RUNNING`, `AUTO`, `ALARM_LED`) have
     * no digit at all, so "no digit means words" would hand a real code to the description
     * search and show a technician the wrong list.
     *
     * `codes.code_norm` rather than `aliases.alias_norm`, for the reason `codePresence` gives:
     * `code_norm` is the canonical form of the code itself, indexed by `idx_codes_norm`, one
     * `LIMIT 1` lookup. An alias is a *variant* of a code, so a query matching only an alias is
     * not necessarily a code.
     *
     * This is deliberately **global**, like `codePresence`: it returns a fact about the
     * knowledge base, never a row, and it is what keeps the scoped search honest — a code that
     * this model does not have must be answered with "no code matches", which is useful
     * information, rather than with codes whose fix steps happen to mention it. It runs at most
     * once per dead-end query, after exact, prefix and free text have all failed.
     */
    private suspend fun isKnownCode(canon: String): Boolean = io {
        db.rawQuery(
            "SELECT 1 FROM codes WHERE code_norm = ? LIMIT 1",
            arrayOf(canon),
        ).firstRow { 1 } != null
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
     * The full search a code field runs, in order:
     *
     *  1. **exact**, then **prefix**, on `aliases` — but only for a query that looks like a
     *     code. A code-looking string is never sent to FTS.
     *  2. **free text** over `code_fts`, quoted and AND-joined.
     *  3. only if that found nothing, and only if the query is not a code anywhere in the
     *     knowledge base, the **description** search over this model's own text.
     *  4. only if *that* found nothing, the same index **OR-joined** — the words this model's
     *     titles do hold, when the words together hold none of them.
     *
     * Each step returns only when it found something, so the later steps cost nothing on any
     * query that already works, and a code this model does not have still ends on step 2's
     * empty list — which is what the screen's "no code matches" message is for.
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
        val text = codesText(scope, SearchInput.ftsQuery(rawQuery))
        if (text.isNotEmpty()) return text
        if (isKnownCode(canon)) return emptyList()
        val described = codesDescription(scope, SearchInput.descriptionTerms(canon))
        if (described.isNotEmpty()) return described
        val loose = SearchInput.ftsQueryAny(rawQuery)
        return if (loose.isEmpty()) emptyList() else codesText(scope, loose)
    }

    companion object {
        /**
         * The description search. Read out of this file, verbatim, by
         * `app-pipeline/check_app_sql.py` (`_kotlin_description_sql`), so there is no second
         * copy to keep in step — and it still runs outside a device, because a JVM unit test
         * cannot reach `android.database.sqlite`.
         *
         * `WHERE_HOLDER` is replaced with one `d.haystack LIKE ?` per term before the query is
         * run: the parameter list is built the same way, in the same order, so a term and its
         * placeholder cannot be separated. The `WITH` is there so the haystack — eight columns
         * and two correlated subqueries — is built once per row of this model line instead of
         * once per term.
         */
        internal val DESCRIPTION_SQL = """
            WITH d AS (
                SELECT c.id, c.uid, c.code, c.title_en, c.title_ur, c.severity, c.is_fault,
                       c.display,
                       lower(
                         coalesce(c.title_en, '') || ' ' || coalesce(c.title_ur, '') || ' ' ||
                         coalesce(c.meaning_en, '') || ' ' || coalesce(c.meaning_ur, '') || ' ' ||
                         coalesce(c.notes_en, '') || ' ' || coalesce(c.notes_ur, '') || ' ' ||
                         coalesce((SELECT group_concat(x.en || ' ' || x.ur)
                                     FROM causes x WHERE x.code_id = c.id), '') || ' ' ||
                         coalesce((SELECT group_concat(x.en || ' ' || x.ur)
                                     FROM solutions x WHERE x.code_id = c.id), '')
                       ) AS haystack
                  FROM codes c
                 WHERE c.series_id = ? AND c.brand_id = ?
            )
            SELECT id, uid, code, title_en, title_ur, severity, is_fault, display
              FROM d
             WHERE %WHERE%
             ORDER BY code COLLATE NOCASE
             LIMIT ?
        """.trimIndent()

        /** Replaced with the per-term `LIKE` predicates; see [DESCRIPTION_SQL]. */
        internal const val WHERE_HOLDER = "%WHERE%"
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
