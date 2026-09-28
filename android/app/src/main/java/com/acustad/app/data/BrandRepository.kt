package com.acustad.app.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * The only queries the home screen needs. Counts are always read live from the database —
 * never hard-coded — so a data release updates the numbers with no app change.
 * (PHASE_3_BROWSE.md §2)
 *
 * Schema notes, verified against the shipped database, because they are easy to get wrong:
 *  - `brands.id` is TEXT (the slug, e.g. "growatt"), not an integer.
 *  - `brands.categories` is a JSON array string, e.g. '["ac"]' or '["ac","inverter"]'.
 *    There is no `brands.unit_type`; `unit_type` lives on `series`.
 *  - `series.category` is a clean single value: 'ac' (99) or 'inverter' (221).
 *  - `codes.brand_id` and `codes.series_id` are both TEXT and both present, so a code list
 *    for a series does not have to join through `series`.
 */
class BrandRepository(context: Context) {

    private val kb = KbDatabase.get(context)

    /** Brand and code counts for the two categories shown on the home screen. */
    suspend fun categoryCounts(): CategoryCounts = withContext(Dispatchers.IO) {
        val db = kb.openReadOnly()
        db.rawQuery(COUNTS_SQL, null).use { c ->
            c.moveToFirst()
            CategoryCounts(
                acBrands = c.getInt(0),
                inverterBrands = c.getInt(1),
                acCodes = c.getInt(2),
                inverterCodes = c.getInt(3),
            )
        }
    }

    /** The knowledge-base version string, shown in Settings so a bug report can be dated. */
    suspend fun databaseVersion(): String = withContext(Dispatchers.IO) {
        kb.openReadOnly().rawQuery("SELECT value FROM meta WHERE key = 'kb_version'", null).use { c ->
            if (c.moveToFirst()) c.getString(0) else "unknown"
        }
    }

    private companion object {
        /**
         * Brand and code counts, verified against the shipped database: 31 + 33 brands and
         * 1,723 + 2,695 = 4,418 codes.
         *
         * Two traps this query exists to avoid:
         *
         * 1. A brand can sit in BOTH categories (2 of the 62 do), so the brand counts sum to
         *    64, not 62. That is the truth about the data, not a bug. Code counts therefore
         *    come from `series.category`, which is a single clean value ('ac' or 'inverter')
         *    and sums to exactly 4,418 with no overlap.
         *
         * 2. Do NOT try to derive code counts by joining `codes` to `series`. `series.id` is
         *    only unique within a brand (6 values repeat, one 14 times), so a bare
         *    `codes.series_id = series.id` join returns 7,036 rows instead of 4,418.
         *    `series.code_count` is a reliable denormalised total (SUM = 4,418), so this
         *    query needs no join at all and stays fast on a low-end phone.
         */
        val COUNTS_SQL = """
            SELECT
              (SELECT COUNT(*) FROM brands WHERE categories LIKE '%"ac"%'),
              (SELECT COUNT(*) FROM brands WHERE categories LIKE '%"inverter"%'),
              (SELECT IFNULL(SUM(code_count), 0) FROM series WHERE category = 'ac'),
              (SELECT IFNULL(SUM(code_count), 0) FROM series WHERE category = 'inverter')
        """.trimIndent()
    }
}

data class CategoryCounts(
    val acBrands: Int,
    val inverterBrands: Int,
    val acCodes: Int,
    val inverterCodes: Int,
)
