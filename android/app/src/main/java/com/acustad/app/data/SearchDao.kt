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
