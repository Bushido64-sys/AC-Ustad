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
    fun `canon never leaves a double space or a trailing space`() {
        assertEquals("E1", SearchInput.canon("  E  1  "))
        assertEquals("E1", SearchInput.canon("E\t1"))
        assertEquals("AB", SearchInput.canon("a   b"))
        assertEquals("", SearchInput.canon("   "))
    }

    @Test
    fun `canon never throws on hostile input`() {
        val nasty = listOf("", "   ", "\"", "'", "%", "_", "\\", "();--", "a".repeat(500), "€£¥")
        for (input in nasty) {
            SearchInput.canon(input) // must not throw
        }
    }

    @Test
    fun `ftsQuery wraps input in double quotes`() {
        assertEquals("\"compressor\"", SearchInput.ftsQuery("compressor"))
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
        assertTrue("must be quoted", blink.startsWith("\"") && blink.endsWith("\""))
        assertTrue("must be quoted", semi.startsWith("\"") && semi.endsWith("\""))
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
}
