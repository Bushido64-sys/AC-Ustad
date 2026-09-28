package com.acustad.app.data

import android.database.sqlite.SQLiteDatabase
import com.acustad.app.model.Brand
import com.acustad.app.model.CategoryCounts
import com.acustad.app.model.KbMeta
import com.acustad.app.model.Series

/**
 * Brands, model lines and metadata.
 *
 * Every method is `suspend` and runs on [kotlinx.coroutines.Dispatchers.IO]. Compose never
 * sees a `Cursor`.
 *
 * The SQL is written out rather than generated, and it is written exactly as the schema
 * actually is — see the traps noted beside each query.
 */
class CatalogDao(private val db: SQLiteDatabase) {

    /**
     * Brand and code counts for the two home-screen categories. Verified: 31 / 33 brands and
     * 1,723 / 2,695 codes, summing to exactly 4,418.
     *
     * No join is used on purpose: `SUM(series.code_count)` is a reliable denormalised total
     * (SUM = 4,418), and joining `codes` to `series` on `series_id` alone returns 7,036 rows
     * because `series.id` is unique only within a brand.
     */
    suspend fun categoryCounts(): CategoryCounts = io {
        db.rawQuery(COUNTS_SQL, null).firstRow { c ->
            CategoryCounts(c.getInt(0), c.getInt(1), c.getInt(2), c.getInt(3))
        } ?: CategoryCounts(0, 0, 0, 0)
    }

    /**
     * Brands in a category, most codes first.
     *
     * 4 of the 31 AC brands and 4 of the 33 inverter brands have zero codes. They are
     * returned, not filtered: they are researched brands with nothing published, which is a
     * fact about the world rather than an error. The UI mutes them.
     */
    suspend fun brandsIn(categoryJsonNeedle: String): List<Brand> = io {
        db.rawQuery(
            """
            SELECT b.id, b.name, b.categories, b.code_count, b.series_count, b.notes
              FROM brands b
             WHERE b.categories LIKE ?
             ORDER BY b.code_count DESC, b.name COLLATE NOCASE
            """.trimIndent(),
            arrayOf("%$categoryJsonNeedle%"),
        ).mapRows { c ->
            Brand(
                id = c.getString(0),
                name = c.getString(1),
                categoriesJson = c.getString(2),
                codeCount = c.getInt(3),
                seriesCount = c.getInt(4),
                notes = c.stringOrNull(5),
            )
        }
    }

    /**
     * The model lines of one brand, most codes first.
     *
     * 1 of Haier's 7 model lines has no codes, and 65 of the 320 across the app have none, so
     * the empty state is a main path rather than an edge case.
     */
    suspend fun seriesOf(brandId: String): List<Series> = io {
        db.rawQuery(
            """
            SELECT s.uid, s.id, s.brand_id, s.name, s.category, s.unit_type, s.code_count, s.notes
              FROM series s
             WHERE s.brand_id = ?
             ORDER BY s.code_count DESC, s.name COLLATE NOCASE
            """.trimIndent(),
            arrayOf(brandId),
        ).mapRows { c ->
            Series(
                uid = c.getString(0),
                seriesId = c.getString(1),
                brandId = c.getString(2),
                name = c.getString(3),
                category = c.getString(4),
                unitType = c.stringOrNull(5),
                codeCount = c.getInt(6),
                notes = c.stringOrNull(7),
            )
        }
    }

    suspend fun meta(): KbMeta = io {
        val values = HashMap<String, String>()
        db.rawQuery("SELECT key, value FROM meta", null).mapRows { c ->
            values[c.getString(0)] = c.getString(1)
        }
        KbMeta(
            kbVersion = values["kb_version"] ?: "unknown",
            builtAt = values["built_at"] ?: "unknown",
            brandCount = values["brands"]?.toIntOrNull() ?: 0,
            seriesCount = values["series"]?.toIntOrNull() ?: 0,
            codeCount = values["codes"]?.toIntOrNull() ?: 0,
        )
    }

    private companion object {
        val COUNTS_SQL = """
            SELECT
              (SELECT COUNT(*) FROM brands WHERE categories LIKE '%"ac"%'),
              (SELECT COUNT(*) FROM brands WHERE categories LIKE '%"inverter"%'),
              (SELECT IFNULL(SUM(code_count), 0) FROM series WHERE category = 'ac'),
              (SELECT IFNULL(SUM(code_count), 0) FROM series WHERE category = 'inverter')
        """.trimIndent()
    }
}
