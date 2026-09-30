package com.acustad.app.ui.browse

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * The decision behind the brands screen's teaching empty state.
 *
 * This is the part of the feature that can be wrong in a way nobody would notice on a phone: a
 * dead-end message that appears for a query it should not, or stays silent for one it should
 * explain, both look like a working screen. It is pure, so it is pinned here.
 */
class BrandSearchTeachingTest {

    // ── the digit gate, which is what decides whether anything is said at all ──

    @Test
    fun `a query with a digit is treated as a code`() {
        // Every one of these is a real shape in the shipped database.
        assertTrue(looksLikeACode("E6"))
        assertTrue(looksLikeACode("F4"))
        assertTrue(looksLikeACode("ID013"))
        assertTrue(looksLikeACode("P003"))
        assertTrue(looksLikeACode("200"))
        assertTrue(looksLikeACode("Error 200"))
        assertTrue(looksLikeACode("LED1 x1 blink"))
    }

    @Test
    fun `a query with no digit is treated as a brand name`() {
        assertFalse(looksLikeACode("sh"))
        assertFalse(looksLikeACode("Sharp"))
        assertFalse(looksLikeACode("dawlance"))
        assertFalse(looksLikeACode(""))
        assertFalse(looksLikeACode("   "))
    }

    @Test
    fun `the digit gate is case-insensitive because a digit is a digit`() {
        assertTrue(looksLikeACode("e6"))
        assertTrue(looksLikeACode("b5"))
    }

    // ── the three outcomes ──

    @Test
    fun `a code on many brands explains the rule and counts them`() {
        val hint = teachingFor("E6", brandCount = 16, brandName = null)
        assertEquals(BrandSearchTeaching.Ambiguous("E6", 16), hint)
    }

    @Test
    fun `a code on exactly one brand is a different sentence, and names the brand`() {
        // "different on 1 brands" is not a sentence a person can read, and the useful fact is
        // different too: the code is real, so the fix is to go one level down.
        val hint = teachingFor("ID013", brandCount = 1, brandName = "Sofar Solar")
        assertEquals(BrandSearchTeaching.OneBrand("ID013", "Sofar Solar"), hint)
    }

    @Test
    fun `one brand with an unnameable brand still teaches, rather than going quiet`() {
        val hint = teachingFor("200", brandCount = 1, brandName = null)
        assertEquals(BrandSearchTeaching.OneBrand("200", ""), hint)
    }

    @Test
    fun `no results at all teaches nothing`() {
        // A brand name that does not exist. The rule being taught is irrelevant here, and a
        // paragraph of explanation dropped on a typo is worse than silence.
        assertEquals(BrandSearchTeaching.None, teachingFor("sharpe", brandCount = 0))
        assertEquals(BrandSearchTeaching.None, teachingFor("xyz", brandCount = 0))
    }

    @Test
    fun `a name-shaped query teaches nothing even if the count is high`() {
        // Defence in depth: `looksLikeACode` already gated the query before it got here, so a
        // stray high count must not produce an explanation about codes.
        assertEquals(BrandSearchTeaching.None, teachingFor("Sharp", brandCount = 16))
    }

    @Test
    fun `an empty query teaches nothing`() {
        assertEquals(BrandSearchTeaching.None, teachingFor("", brandCount = 16))
        assertEquals(BrandSearchTeaching.None, teachingFor("   ", brandCount = 16))
    }

    @Test
    fun `a negative count is treated as no result rather than trusted`() {
        // The DAO cannot return one, but this runs on a path that must never produce a
        // nonsensical sentence, and a negative is the obvious way in.
        assertEquals(BrandSearchTeaching.None, teachingFor("E6", brandCount = -1))
    }

    @Test
    fun `the technician's own casing is echoed back, not corrected`() {
        // They typed `e6`. The message says `e6`. Correcting their casing teaches nothing and
        // reads as being talked down to.
        assertEquals(BrandSearchTeaching.Ambiguous("e6", 16), teachingFor("e6", 16))
    }

    @Test
    fun `surrounding whitespace is trimmed off the echoed code`() {
        assertEquals(BrandSearchTeaching.Ambiguous("E6", 16), teachingFor("  E6  ", 16))
    }

    // ── the fourth outcome: fault words typed where a brand name was expected ──

    @Test
    fun `a phrase describing a fault says where fault words are searched`() {
        // The report this exists for: "air leakage" returns nothing on the brands screen, and a
        // bare "No brand matches" reads as a broken search rather than as the wrong box.
        assertEquals(
            BrandSearchTeaching.Description("air leakage"),
            teachingFor("air leakage", brandCount = 0),
        )
        assertEquals(
            BrandSearchTeaching.Description("not cooling"),
            teachingFor("  not cooling  ", brandCount = 0),
        )
        assertEquals(
            BrandSearchTeaching.Description("gas leak"),
            teachingFor("gas leak", brandCount = 0),
        )
    }

    @Test
    fun `a one-word miss still teaches nothing, however true the description would be`() {
        // The bias the rest of this file is built around: a paragraph dropped on a typo is
        // worse than no paragraph. "compressor" could be a mistyped brand just as easily as a
        // fault, and here it gets the plain miss.
        assertEquals(BrandSearchTeaching.None, teachingFor("compressor", brandCount = 0))
        assertEquals(BrandSearchTeaching.None, teachingFor("leakage", brandCount = 0))
    }

    @Test
    fun `a phrase containing a digit stays a code question, never a description`() {
        // `Error 200` and `LED1 x1 blink` are codes. The digit half of the rule is what keeps
        // them on the code path even though both contain a space.
        assertEquals(BrandSearchTeaching.None, teachingFor("Error 200", brandCount = 0))
        assertEquals(BrandSearchTeaching.None, teachingFor("LED1 x1 blink", brandCount = 0))
        assertFalse(isDescription("Error 200"))
    }

    @Test
    fun `isDescription needs both halves of the rule`() {
        assertTrue(isDescription("air leakage"))
        assertTrue(isDescription("high pressure"))
        // no space -> one word -> possibly a typo, so not a description
        assertFalse(isDescription("compressor"))
        assertFalse(isDescription("dawlance"))
        // a digit -> possibly a code, so not a description
        assertFalse(isDescription("3 phase"))
        assertFalse(isDescription("E6"))
        // blank input is nothing at all, and must not read as a description of a fault
        assertFalse(isDescription(""))
        assertFalse(isDescription("   "))
    }

    @Test
    fun `a description state never appears for a query that is a real code somewhere`() {
        // Defence in depth: the view model only asks about a query with brandCount 0, but a
        // caller that has measured a count must not be able to turn a code into fault words.
        assertEquals(BrandSearchTeaching.Ambiguous("E6", 16), teachingFor("E6", brandCount = 16))
        assertEquals(
            BrandSearchTeaching.OneBrand("ID013", "Sofar Solar"),
            teachingFor("ID013", brandCount = 1, brandName = "Sofar Solar"),
        )
    }
}
