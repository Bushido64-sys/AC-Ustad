package com.acustad.app.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Pure-logic tests, no device and no database. If these fail, every code search in the app
 * is wrong - which is the single most important behaviour in the product.
 */
class SearchInputTest {

    @Test
    fun `normalise lower-cases and trims`() {
        assertEquals("e1", SearchInput.normalise("  E1  "))
        assertEquals("error 200", SearchInput.normalise("Error 200"))
    }

    @Test
    fun `normalise keeps the characters a real code uses`() {
        // These are the characters that must survive, because real codes contain them.
        assertEquals("blink-running", SearchInput.normalise("BLINK-RUNNING"))
        assertEquals("er 2", SearchInput.normalise("Er 2"))
        assertEquals("p003", SearchInput.normalise("P003"))
        assertEquals("id f101", SearchInput.normalise("ID F101"))
    }

    @Test
    fun `normalise collapses whitespace and drops everything unsafe`() {
        assertEquals("e1", SearchInput.normalise("E  1"))
        assertEquals("e1", SearchInput.normalise("E\t1"))
        assertEquals("ab", SearchInput.normalise("a*b"))
        assertEquals("ab", SearchInput.normalise("a; DROP TABLE codes;--"))
    }

    @Test
    fun `normalise never throws on hostile input`() {
        val nasty = listOf("", "   ", "\"", "'", "%", "_", "\\\\", "();--", "a".repeat(500))
        for (input in nasty) {
            SearchInput.normalise(input) // must not throw
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
        assertTrue(SearchInput.looksLikeCode("e1"))
        assertTrue(SearchInput.looksLikeCode("error200"))
        assertFalse("a phrase is not a code", SearchInput.looksLikeCode("compressor fault"))
        assertFalse(SearchInput.looksLikeCode(""))
    }

    @Test
    fun `looksLikeCode rejects absurdly long input`() {
        assertFalse(SearchInput.looksLikeCode("a".repeat(500)))
    }
}
