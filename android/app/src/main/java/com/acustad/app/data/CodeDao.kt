package com.acustad.app.data

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.acustad.app.model.BilingualText
import com.acustad.app.model.CodeDetail
import com.acustad.app.model.CodeSummary
import com.acustad.app.model.ScopedSeries

/**
 * Reading individual codes.
 *
 * The one rule this file exists to enforce: **a code list is always scoped to a model line,
 * and a model line is identified by `series_id` AND `brand_id` together.** `series.id` is
 * unique only within a brand — 6 values repeat, `inverter-split` 14 times — so a query that
 * binds only `series_id` returns another brand's codes. The signature takes a
 * [ScopedSeries] rather than two loose strings so that omission is not possible.
 */
class CodeDao(private val db: SQLiteDatabase) {

    /**
     * The codes of one model line, sorted the way a technician reads them.
     *
     * `display` is the display-STYLE hint (`controller`, `indoor`, `led_blink`, …), not a
     * pre-joined title, so the code and both titles are selected separately. Largest model
     * line is 106 rows, so this is loaded whole; paging is not warranted.
     */
    suspend fun codesIn(scope: ScopedSeries): List<CodeSummary> = io {
        db.rawQuery(
            """
            SELECT c.id, c.uid, c.code, c.title_en, c.title_ur, c.severity, c.is_fault, c.display
              FROM codes c
             WHERE c.series_id = ? AND c.brand_id = ?
             ORDER BY c.code COLLATE NOCASE
            """.trimIndent(),
            arrayOf(scope.seriesId, scope.brandId),
        ).mapRows { it.toSummary() }
    }

    /**
     * One code, in ONE query, with both languages and its causes and fix steps.
     *
     * The causes and solutions come back as separate lists from LEFT JOINs rather than as
     * one fanned-out result set: a detail screen is read once and held, and splitting the
     * rows here avoids the index arithmetic that de-duplicating a joined result would need.
     */
    suspend fun detailById(id: Long, isFavourite: Boolean): CodeDetail? = io {
        val head = db.rawQuery(DETAIL_SQL, arrayOf(id.toString())).firstRow { c ->
            CodeDetail(
                summary = c.toSummary(),
                meaningEn = c.stringOrNull(9),
                meaningUr = c.stringOrNull(10),
                notesEn = c.stringOrNull(11),
                notesUr = c.stringOrNull(12),
                confidence = c.getString(13),
                sourceType = c.stringOrNull(14),
                sourceTitle = c.stringOrNull(15),
                sourceUrl = c.stringOrNull(16),
                blinkPattern = c.stringOrNull(17),
                relatedCodes = c.stringOrNull(18),
                seriesId = c.getString(19),
                brandId = c.getString(20),
                causes = emptyList(),
                solutions = emptyList(),
                isFavourite = isFavourite,
            )
        } ?: return@io null

        val causes = db.rawQuery(
            "SELECT idx, en, ur FROM causes WHERE code_id = ? ORDER BY idx",
            arrayOf(head.summary.id.toString()),
        ).mapRows { BilingualText(it.getInt(0), it.stringOrNull(1), it.stringOrNull(2)) }

        val solutions = db.rawQuery(
            "SELECT idx, en, ur FROM solutions WHERE code_id = ? ORDER BY idx",
            arrayOf(head.summary.id.toString()),
        ).mapRows { BilingualText(it.getInt(0), it.stringOrNull(1), it.stringOrNull(2)) }

        head.copy(causes = causes, solutions = solutions)
    }



    private companion object {
        /** Column order must match [toSummary] (0-7) and [detailById] (8-20). */
        val DETAIL_SQL = """
            SELECT c.id, c.uid, c.code, c.title_en, c.title_ur, c.severity, c.is_fault, c.display,
                   c.meaning_en, c.meaning_ur, c.notes_en, c.notes_ur, c.confidence,
                   c.source_type, c.source_title, c.source_url, c.blink_pattern, c.related_codes,
                   c.series_id, c.brand_id
              FROM codes c
             WHERE c.id = ?
        """.trimIndent()
    }
}

/** Columns 0–7 are the summary columns in every query in this file. */
internal fun Cursor.toSummary() = CodeSummary(
    id = getLong(0),
    uid = getString(1),
    code = getString(2),
    titleEn = stringOrNull(3),
    titleUr = stringOrNull(4),
    severity = getString(5),
    isFault = getInt(6) == 1,
    display = getString(7),
)

/** `getString` returns "" for SQL NULL; the database has a lot of legitimately absent text. */
internal fun Cursor.stringOrNull(index: Int): String? = getString(index).ifBlank { null }
