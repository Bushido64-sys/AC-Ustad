package com.acustad.app.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Pure-logic tests, no device and no database. If these fail, every code search in the app
 * is wrong — the single most important behaviour in the product.
 *
 * The expectations below are the values stored in the shipped database's `alias_norm`
 * column, not opinions about how codes "should" look.
 */
class SearchInputTest {

    @Test
    fun `canon upper-cases, because alias_norm is stored upper-cased`() {
        // THE bug this file exists to prevent: lower-casing returned zero rows for 'e1'.
        assertEquals("E1", SearchInput.canon("e1"))
        assertEquals("E1", SearchInput.canon("E1"))
        assertEquals("E6", SearchInput.canon("e6"))
        assertEquals("F4", SearchInput.canon("f4"))
    }

    @Test
    fun `canon keeps the punctuation real codes use`() {
        assertEquals("BLINK-RUNNING", SearchInput.canon("blink-running"))
        assertEquals("ISO_FAIL", SearchInput.canon("iso_fail"))
        assertEquals("ID F101", SearchInput.canon("ID F101"))
        assertEquals("ERROR 200", SearchInput.canon("Error 200"))
        assertEquals("BEEP-2.5SEC", SearchInput.canon("BEEP-2.5SEC"))
    }

    @Test
    fun `canon turns a leading digit pattern into the stored form`() {
        // The database stores '003' with a leading zero; a technician types '3' on the keypad
        // and still has to find it, which is what the prefix path is for.
        assertEquals("P003", SearchInput.canon("P003"))
        assertEquals("P003", SearchInput.canon("p003"))
    }

    @Test
    fun `canon collapses other punctuation to single spaces`() {
        // 'LED1 x1 blink; LED2 off; LED3 off' is stored as
        // 'LED1 X1 BLINK LED2 OFF LED3 OFF'
        assertEquals(
            "LED1 X1 BLINK LED2 OFF LED3 OFF",
            SearchInput.canon("LED1 x1 blink; LED2 off; LED3 off"),
        )
        // 'GRID-INTF. (1030 DATA:0000)' is stored as 'GRID-INTF. 1030 DATA 0000'
        assertEquals(
            "GRID-INTF. 1030 DATA 0000",
            SearchInput.canon("GRID-INTF. (1030 DATA:0000)"),
        )
    }

    @Test
    fun `canon keeps single spaces but never doubles or trails them`() {
        // A space is a REAL separator in alias_norm - 'High Temp' is stored as 'HIGH TEMP',
        // 'LED1 x1 blink; LED2 off' as 'LED1 X1 BLINK LED2 OFF'. So canon must keep one
        // space and drop any extra. An earlier version of this test expected spaces to be
        // deleted entirely ('E 1' -> 'E1'), which contradicts the database and would have
        // broken every multi-word code.
        assertEquals("E 1", SearchInput.canon("  E  1  "))
        assertEquals("E 1", SearchInput.canon("E\t1"))
        assertEquals("A B", SearchInput.canon("a   b"))
        assertEquals("", SearchInput.canon("   "))
    }

    @Test
    fun `canon reproduces the stored multi-word forms exactly`() {
        // These four pairs are copied from the shipped database, not invented.
        assertEquals("HIGH TEMP", SearchInput.canon("High Temp"))
        assertEquals("88 ERROR", SearchInput.canon("88 error"))
        assertEquals(
            "LED1 X1 BLINK LED2 OFF LED3 OFF",
            SearchInput.canon("LED1 x1 blink; LED2 off; LED3 off"),
        )
        assertEquals("GRID-INTF. 1030 DATA 0000", SearchInput.canon("GRID-INTF. (1030 DATA:0000)"))
    }

    @Test
    fun `canon never throws on hostile input`() {
        val nasty = listOf("", "   ", "\"", "'", "%", "_", "\\", "();--", "a".repeat(500), "€£¥")
        for (input in nasty) {
            SearchInput.canon(input) // must not throw
        }
    }

    @Test
    fun `ftsQuery quotes a single term`() {
        assertEquals("\"compressor\"", SearchInput.ftsQuery("compressor"))
    }

    @Test
    fun `ftsQuery AND-joins terms instead of demanding an exact phrase`() {
        // A technician types words, not phrases. A single quoted string is an FTS5 phrase, so
        // "over current" would only match those exact adjacent words in that order - measured at
        // 110 hits against 133 for the AND form. Requiring adjacency hides real answers.
        assertEquals("\"over\" AND \"current\"", SearchInput.ftsQuery("over current"))
        assertEquals("\"a\" AND \"b\" AND \"c\"", SearchInput.ftsQuery("a b c"))
    }

    @Test
    fun `ftsQuery collapses odd whitespace rather than emitting empty quoted terms`() {
        // An empty term would become "" and match nothing, or worse, be a syntax error.
        assertEquals("\"a\" AND \"b\"", SearchInput.ftsQuery("  a   b  "))
        // A tab is a separator, not a character to search for, so this is TWO terms.
        assertEquals("\"a\" AND \"b\"", SearchInput.ftsQuery("a\tb"))
        assertEquals("\"a\" AND \"b\"", SearchInput.ftsQuery("a\nb"))
        assertEquals("", SearchInput.ftsQuery("   "))
        assertEquals("", SearchInput.ftsQuery("\t \n  "))
    }

    @Test
    fun `ftsQuery escapes embedded double quotes by doubling them`() {
        assertEquals("\"a\"\"b\"", SearchInput.ftsQuery("a\"b"))
    }

    @Test
    fun `ftsQuery makes the known-crashing inputs safe`() {
        // These two crash SQLite when passed to MATCH unquoted.
        val blink = SearchInput.ftsQuery("BLINK-RUNNING")
        val semi = SearchInput.ftsQuery("LED1 x1 blink; LED2 off")
        val plus = SearchInput.ftsQuery("a+b")
        // Every term quoted individually: no bare word can reach the MATCH clause.
        assertEquals("\"BLINK-RUNNING\"", blink)
        assertEquals("\"LED1\" AND \"x1\" AND \"blink;\" AND \"LED2\" AND \"off\"", semi)
        assertEquals("\"a+b\"", plus)
    }

    @Test
    fun `looksLikeCode is true for codes and false for descriptions`() {
        assertTrue(SearchInput.looksLikeCode(SearchInput.canon("e1")))
        assertTrue(SearchInput.looksLikeCode(SearchInput.canon("blink-running")))
        assertFalse("a phrase is not a code", SearchInput.looksLikeCode(SearchInput.canon("compressor fault")))
        assertFalse(SearchInput.looksLikeCode(""))
    }

    @Test
    fun `looksLikeCode rejects absurdly long input`() {
        assertFalse(SearchInput.looksLikeCode("A".repeat(500)))
    }

    // ── descriptionTerms: the words the fallback search ANDs together ────────

    @Test
    fun `descriptionTerms splits a fault description into its words`() {
        // The case a technician actually reports: words, not a code, typed into a search box.
        assertEquals(
            listOf("AIR", "LEAKAGE"),
            SearchInput.descriptionTerms(SearchInput.canon("air leakage")),
        )
        assertEquals(
            listOf("NOT", "COOLING"),
            SearchInput.descriptionTerms(SearchInput.canon("not cooling")),
        )
        assertEquals(listOf("COMPRESSOR"), SearchInput.descriptionTerms(SearchInput.canon("compressor")))
    }

    @Test
    fun `descriptionTerms tokenises the way unicode61 does, so both paths agree on a word`() {
        // Punctuation is a separator in the FTS index, so it must be one here too - otherwise a
        // title match and a description match would disagree about what the user typed.
        assertEquals(listOf("AIR", "LEAKAGE"), SearchInput.descriptionTerms(SearchInput.canon("air-leakage")))
        assertEquals(listOf("ISO", "FAIL"), SearchInput.descriptionTerms(SearchInput.canon("iso_fail")))
        assertEquals(
            listOf("LED1", "X1", "BLINK"),
            SearchInput.descriptionTerms(SearchInput.canon("led1 x1 blink;")),
        )
        // The trailing bang must not become part of the term: LIKE '%LEAKAGE!%' matches nothing.
        assertEquals(
            listOf("AIR", "LEAKAGE"),
            SearchInput.descriptionTerms(SearchInput.canon("air leakage!")),
        )
    }

    @Test
    fun `a one-character term is dropped, because LIKE against it is a scan not a search`() {
        // The field is debounced, so a technician pausing halfway through `3 phase` really does
        // get queried on `3`. LIKE '%3%' would answer with most of the model line.
        assertEquals(listOf("PHASE"), SearchInput.descriptionTerms(SearchInput.canon("3 phase")))
        assertEquals(emptyList<String>(), SearchInput.descriptionTerms(SearchInput.canon("a")))
        assertEquals(emptyList<String>(), SearchInput.descriptionTerms(SearchInput.canon("1")))
        assertEquals(emptyList<String>(), SearchInput.descriptionTerms(SearchInput.canon("   ")))
    }

    @Test
    fun `descriptionTerms cannot emit a LIKE wildcard from user input`() {
        // No ESCAPE clause is written, so this is the guarantee that one is not needed: a `%`
        // or `_` is a separator and never a term, so `100%` cannot mean "everything".
        val nasty = listOf("100%", "%", "_", "a_b", "\\", "();--", "€£¥", "a".repeat(500))
        for (input in nasty) {
            for (term in SearchInput.descriptionTerms(SearchInput.canon(input))) {
                assertTrue(
                    "term '$term' from input '$input' contains a character LIKE would treat " +
                        "as a wildcard, and the query has no ESCAPE clause",
                    term.all { it.isLetterOrDigit() && it.code < 128 },
                )
            }
        }
    }
}
