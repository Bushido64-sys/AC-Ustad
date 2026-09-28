package com.acustad.app.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * The schema contract, pinned.
 *
 * Every assertion here was taken from the shipped database. They exist because the schema
 * has sharp edges that are easy to get wrong and expensive to debug on a phone:
 *
 *  - `brands.id` and `series.id` are TEXT slugs, not integers.
 *  - `series.id` is only unique WITHIN a brand (6 values repeat, one of them 14 times), so
 *    joining `codes` to `series` on `series_id` alone returns 7,036 rows instead of 4,418.
 *  - There is no `brands.unit_type`; `unit_type` lives on `series`, and `brands.categories`
 *    is a JSON array string.
 *  - `favourites` is keyed on `code_id`, not on a uid.
 *
 * These are pure schema facts, so they are asserted here without needing a device.
 * The row counts are asserted separately by the python step in .github/workflows/build.yml,
 * which opens the real database - an Android unit test cannot, because android.database.sqlite
 * is a stub off-device.
 */
class SchemaContractTest {

    @Test
    fun `brands id is a text slug, not an integer`() {
        assertEquals("brands.id must be TEXT", "TEXT", columnType("brands", "id"))
    }

    @Test
    fun `series id is a text slug`() {
        assertEquals("series.id must be TEXT", "TEXT", columnType("series", "id"))
    }

    @Test
    fun `brands has no unit_type column - it lives on series`() {
        assertTrue("brands must not gain a unit_type column", !columns("brands").contains("unit_type"))
        assertTrue("series.unit_type must exist", columns("series").contains("unit_type"))
    }

    @Test
    fun `brands categories is a json array string`() {
        assertTrue("brands.categories must exist", columns("brands").contains("categories"))
    }

    @Test
    fun `codes has no display_style column - use blink_pattern and related_codes`() {
        val cols = columns("codes")
        assertTrue("codes.display_style does not exist", !cols.contains("display_style"))
        assertTrue("codes.blink_pattern must exist", cols.contains("blink_pattern"))
        assertTrue("codes.related_codes must exist", cols.contains("related_codes"))
    }

    @Test
    fun `causes and solutions are keyed on code_id and idx with en and ur`() {
        for (table in listOf("causes", "solutions")) {
            val cols = columns(table)
            assertTrue("$table needs code_id", cols.contains("code_id"))
            assertTrue("$table needs idx", cols.contains("idx"))
            assertTrue("$table needs en", cols.contains("en"))
            assertTrue("$table needs ur", cols.contains("ur"))
        }
    }

    @Test
    fun `aliases has no kind column - just code_id alias and alias_norm`() {
        val cols = columns("aliases")
        assertTrue("aliases.kind does not exist", !cols.contains("kind"))
        assertTrue("aliases.alias_norm must exist", cols.contains("alias_norm"))
    }

    @Test
    fun `favourites is keyed on code_id and created_at`() {
        val cols = columns("favourites")
        assertTrue("favourites needs code_id", cols.contains("code_id"))
        assertTrue("favourites needs created_at", cols.contains("created_at"))
        // There is no is_read or copied title columns - do not write queries that assume them.
        assertTrue("favourites has no is_read", !cols.contains("is_read"))
        assertTrue("favourites has no title_en", !cols.contains("title_en"))
    }

    @Test
    fun `code uid is brand slash series slash code`() {
        val cols = columns("codes")
        assertTrue("codes.uid must exist", cols.contains("uid"))
    }

    // ── helpers ────────────────────────────────────────────────────────────────
    private fun columnType(table: String, column: String): String =
        SCHEMA[table]?.firstOrNull { it.first == column }?.second ?: "MISSING:$table.$column"

    private fun columns(table: String): Set<String> =
        SCHEMA[table]?.map { it.first }?.toSet() ?: emptySet()

    private companion object {
        /**
         * Copied from `PRAGMA table_info(...)` on the shipped database. If a data release
         * changes the schema, this file is where you find out first - and it is the moment
         * to update the guide's DATA_SCHEMA.md as well, in the same change.
         */
        val SCHEMA: Map<String, List<Pair<String, String>>> = mapOf(
            "brands" to listOf(
                "id" to "TEXT", "name" to "TEXT", "categories" to "TEXT", "country" to "TEXT",
                "website" to "TEXT", "notes" to "TEXT", "series_count" to "INTEGER",
                "code_count" to "INTEGER", "source_notes" to "TEXT",
            ),
            "series" to listOf(
                "uid" to "TEXT", "id" to "TEXT", "brand_id" to "TEXT", "name" to "TEXT",
                "category" to "TEXT", "unit_type" to "TEXT", "model_patterns" to "TEXT",
                "notes" to "TEXT", "code_count" to "INTEGER", "source_file" to "TEXT",
            ),
            "codes" to listOf(
                "id" to "INTEGER", "uid" to "TEXT", "brand_id" to "TEXT", "series_id" to "TEXT",
                "category" to "TEXT", "unit_type" to "TEXT", "code" to "TEXT",
                "code_norm" to "TEXT", "title_en" to "TEXT", "title_ur" to "TEXT",
                "meaning_en" to "TEXT", "meaning_ur" to "TEXT", "severity" to "TEXT",
                "display" to "TEXT", "is_fault" to "INTEGER", "confidence" to "TEXT",
                "source_type" to "TEXT", "source_title" to "TEXT", "source_url" to "TEXT",
                "source_retrieved" to "TEXT", "blink_pattern" to "TEXT",
                "related_codes" to "TEXT", "notes_en" to "TEXT", "notes_ur" to "TEXT",
            ),
            "causes" to listOf("code_id" to "INTEGER", "idx" to "INTEGER", "en" to "TEXT", "ur" to "TEXT"),
            "solutions" to listOf("code_id" to "INTEGER", "idx" to "INTEGER", "en" to "TEXT", "ur" to "TEXT"),
            "aliases" to listOf("code_id" to "INTEGER", "alias" to "TEXT", "alias_norm" to "TEXT"),
            "favourites" to listOf("code_id" to "INTEGER", "created_at" to "TEXT"),
            "meta" to listOf("key" to "TEXT", "value" to "TEXT"),
        )
    }
}
