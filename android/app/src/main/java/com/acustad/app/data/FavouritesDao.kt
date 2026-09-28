package com.acustad.app.data

import android.database.sqlite.SQLiteDatabase
import com.acustad.app.model.FavouriteItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * The only table in the app that is ever written. (RULES.md RULE 5)
 *
 * The shipped table is exactly:
 * ```sql
 * CREATE TABLE favourites (
 *   code_id    INTEGER PRIMARY KEY REFERENCES codes(id) ON DELETE CASCADE,
 *   created_at TEXT NOT NULL
 * )
 * ```
 * Two columns. No `code_uid`, no `is_read`, no copied brand or series names — an earlier
 * version of the build guide invented all of them, and queries written against that fiction
 * would throw.
 *
 * Because it is keyed on `code_id`, and a data release can renumber `codes.id`, a saved row
 * can end up pointing at a **different code**. [sweepStaleIds] is the whole answer, and it
 * runs every time the database is (re)staged.
 */
class FavouritesDao(private val db: SQLiteDatabase) {

    /** Newest first. Joined back to `codes` and `series` for display, because the saved row
     *  stores no names of its own. */
    suspend fun items(): List<FavouriteItem> = io {
        db.rawQuery(JOINED_SQL, null).mapRows { c ->
            FavouriteItem(
                codeId = c.getLong(0),
                code = c.getString(1),
                titleEn = c.stringOrNull(2),
                titleUr = c.stringOrNull(3),
                severity = c.getString(4),
                brandId = c.getString(5),
                brandName = c.getString(6),
                seriesUid = c.getString(7),
                seriesId = c.getString(8),
                seriesName = c.getString(9),
                createdAt = c.getString(10),
            )
        }
    }

    suspend fun codeIds(): List<Long> = io {
        db.rawQuery("SELECT code_id FROM favourites", null).mapRows { it.getLong(0) }
    }

    suspend fun isFavourite(codeId: Long): Boolean = io {
        db.rawQuery(
            "SELECT 1 FROM favourites WHERE code_id = ? LIMIT 1",
            arrayOf(codeId.toString()),
        ).firstRow { true } == true
    }

    /** Adds or removes, in one transaction, so a tap can never half-apply. */
    suspend fun set(codeId: Long, favourite: Boolean) = io {
        db.beginTransaction()
        try {
            if (favourite) {
                // OR IGNORE, not OR REPLACE. A save is a statement about *when* the user saved
                // the code, and a re-save of a row that already exists must not rewrite that
                // time and jump the code to the top of a newest-first list. It also makes the
                // write idempotent in time as well as in existence, so a double tap that slips
                // past the debounce cannot reorder the list either.
                db.execSQL(
                    "INSERT OR IGNORE INTO favourites (code_id, created_at) VALUES (?, ?)",
                    arrayOf(codeId, timestamp()),
                )
            } else {
                db.delete("favourites", "code_id = ?", arrayOf(codeId.toString()))
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    /**
     * Puts a removed row back, keeping the timestamp it had.
     *
     * This is the whole of Undo. Re-saving through [set] would stamp a new `created_at` and
     * the row would land at the top of the list instead of back in the position the user
     * removed it from, which reads as a different list rather than the same one.
     *
     * One statement, so it needs no explicit transaction: a single INSERT is already atomic,
     * and OR REPLACE makes pressing Undo twice harmless.
     */
    suspend fun restore(codeId: Long, createdAt: String) = io {
        db.execSQL(
            "INSERT OR REPLACE INTO favourites (code_id, created_at) VALUES (?, ?)",
            arrayOf(codeId, createdAt),
        )
    }

    /**
     * Drops saved rows whose code no longer exists.
     *
     * Call this after the database has been (re)staged, which is the only moment `code_id`
     * can have moved. Returns how many were dropped so a data release can be logged.
     */
    suspend fun sweepStaleIds(): Int = io {
        db.delete(
            "favourites",
            "code_id NOT IN (SELECT id FROM codes)",
            null,
        )
    }

    /** `created_at` is TEXT in the schema, so an ISO-8601 UTC string is written, not a Long. */
    private fun timestamp(): String =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
            .apply { timeZone = TimeZone.getTimeZone("UTC") }
            .format(Date())

    private companion object {
        /**
         * The saved list's one query, newest first.
         *
         * Two joins matter here and both are two-part:
         *
         *  - `series` is joined on `id` **and** `brand_id`, because `series.id` is unique only
         *    within a brand and `inverter-split` is shared by 14 of them. Joining on the id
         *    alone fanned the row out 14 times and would show a Dawlance code under a
         *    Growatt model name.
         *  - `f.created_at` is selected so the row can be put back exactly where it was by
         *    Undo, rather than re-inserted with a new timestamp at the top of the list.
         */
        val JOINED_SQL = """
            SELECT f.code_id, c.code, c.title_en, c.title_ur, c.severity,
                   c.brand_id, b.name,
                   s.uid, s.id, s.name,
                   f.created_at
              FROM favourites f
              JOIN codes  c ON c.id = f.code_id
              JOIN brands b ON b.id = c.brand_id
              JOIN series s ON s.id = c.series_id AND s.brand_id = c.brand_id
             ORDER BY f.created_at DESC
        """.trimIndent()
    }
}
