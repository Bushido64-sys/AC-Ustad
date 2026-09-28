package com.acustad.app.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Pure-model tests, no device and no database. These pin behaviour that is easy to get
 * subtly wrong and expensive to notice on a phone.
 *
 * The facts they assert were read from the shipped database.
 */
class ModelsTest {

    @Test
    fun `display number is the stored index plus one`() {
        // `causes.idx` and `solutions.idx` are 0-based and contiguous (verified: min 0,
        // max 4 for causes; min 0 for solutions; no code has a gap). A technician reads
        // "step 1", never "step 0", so the displayed number is index + 1.
        assertEquals(1, BilingualText(0, "Step one", "Pehla kadam").displayNumber)
        assertEquals(2, BilingualText(1, "Step two", "Doosra kadam").displayNumber)
        assertEquals(5, BilingualText(4, null, null).displayNumber)
    }

    @Test
    fun `language picks the requested side`() {
        val t = BilingualText(0, "Compressor fault", "Compressor fault hai")
        assertEquals("Compressor fault", t.text(ContentLanguage.EN))
        assertEquals("Compressor fault hai", t.text(ContentLanguage.UR))
    }

    @Test
    fun `language falls back rather than showing an empty box`() {
        // 2,467 of 4,418 codes have no note in either language, and none has a note in only
        // one. The fallback still matters: a half-translated field must never render blank.
        assertEquals("only en", ContentLanguage.UR.pick("only en", null))
        assertEquals("only ur", ContentLanguage.EN.pick(null, "only ur"))
        assertNull(ContentLanguage.EN.pick(null, null))
        assertNull(ContentLanguage.UR.pick("", "   "))
    }

    @Test
    fun `a brand knows which categories it belongs to`() {
        val ac = Brand("hitachi", "Hitachi", "[\"ac\"]", 93, 5, null)
        val inverter = Brand("growatt", "Growatt", "[\"inverter\"]", 125, 9, null)
        val both = Brand("inverex", "Inverex", "[\"ac\", \"inverter\"]", 40, 4, null)

        assertTrue(ac.isIn(CategoryId.AC))
        assertFalse(ac.isIn(CategoryId.INVERTER))
        assertTrue(inverter.isIn(CategoryId.INVERTER))
        assertFalse(inverter.isIn(CategoryId.AC))

        // Homage and Inverex are the two brands listed in both, which is why the two
        // per-category brand counts sum to 64 rather than 62.
        assertTrue(both.isIn(CategoryId.AC))
        assertTrue(both.isIn(CategoryId.INVERTER))
    }

    @Test
    fun `a series carries the two-part identity a scoped query needs`() {
        val s = Series(
            uid = "dawlance/inverter-split",
            seriesId = "inverter-split",
            brandId = "dawlance",
            name = "Splits",
            category = "ac",
            unitType = "split",
            codeCount = 41,
            notes = null,
        )
        // `inverter-split` is the series id shared by 14 different brands, so the bare id is
        // not a key on its own. Every scoped query needs the pair.
        assertEquals(ScopedSeries("inverter-split", "dawlance"), s.scope)
    }

    @Test
    fun `a code summary title follows the content language`() {
        val s = CodeSummary(
            id = 1L,
            uid = "growatt/growatt-mod-tl3x/E6",
            code = "E6",
            titleEn = "Compressor drive overcurrent",
            titleUr = "Compressor IPM fault",
            severity = "stop_pro",
            isFault = true,
            display = "controller",
        )
        assertEquals("Compressor drive overcurrent", s.title(ContentLanguage.EN))
        assertEquals("Compressor IPM fault", s.title(ContentLanguage.UR))
    }

    @Test
    fun `a detail carries the two-part scope of the machine it came from`() {
        // The screen needs this to name the machine, and it is the same pair every scoped query
        // must bind. "inverter-split" is shared by 14 brands, so the brand is not optional.
        assertEquals(ScopedSeries("inverter-split", "dawlance"), detail(sourceUrl = null).scope)
    }

    @Test
    fun `a detail screen hides an absent source rather than showing a dead link`() {
        // 26 codes have no source_url. A link must only be offered when one exists.
        val withUrl = detail(sourceUrl = "https://example.com/manual.pdf")
        val withoutUrl = detail(sourceUrl = null)
        assertEquals("https://example.com/manual.pdf", withUrl.sourceUrl)
        assertNull(withoutUrl.sourceUrl)
        assertEquals("service_manual", withoutUrl.sourceType)
    }

    @Test
    fun `a detail screen carries empty cause and solution lists when the data has none`() {
        // `panasonic/panasonic-hf-self-diagnosis/H00` is the one code with no causes, and it
        // is is_fault = 0. The UI hides an empty block; the model must not invent content.
        val d = detail(sourceUrl = null)
        assertTrue(d.causes.isEmpty())
        assertTrue(d.solutions.isEmpty())
        assertFalse(d.isFavourite)
    }

    @Test
    fun `a saved row carries the timestamp Undo has to write back`() {
        // `favourites` is (code_id, created_at) and nothing else, so the timestamp is the only
        // thing the row has that the join cannot rebuild. If it were dropped from the model,
        // Undo would re-save with a fresh time and the row would jump to the top of a
        // newest-first list instead of returning to where the user put it.
        val item = favourite()
        assertEquals("2026-03-04T09:15:00Z", item.createdAt)
        assertEquals("E6", item.title(ContentLanguage.EN))
        assertEquals("Compressor IPM fault", item.title(ContentLanguage.UR))
    }

    @Test
    fun `a saved row is identified by the code id, never by its code string`() {
        // E1 is on 20 brands. A saved row is a row in `favourites`, which is keyed on
        // codes.id, so two identical code strings on two machines are two different saved rows
        // and must never be merged. (RULES.md RULE 2)
        val dawlance = favourite()
        val growatt = favourite().copy(codeId = 9999L, brandId = "growatt", brandName = "Growatt")
        assertTrue(dawlance.code == growatt.code)
        assertFalse(dawlance.codeId == growatt.codeId)
    }

    @Test
    fun `a saved row carries the two-part scope of the machine it came from`() {
        assertEquals(ScopedSeries("inverter-split", "dawlance"), favourite().scope)
    }

    @Test
    fun `a saved row knows whether its code is a fault at all`() {
        // 569 of the 4,418 codes are indicators, parameters and self-clear entries. A saved one
        // of those must be rendered with the word INDICATOR and a muted rail, never with a
        // severity word, or the app claims a breakdown that is not there.
        assertTrue(favourite().isFault)
        val indicator = favourite().copy(code = "P003", codeId = 2L, isFault = false)
        assertFalse(indicator.isFault)
        assertEquals("P003", indicator.code)
    }

    private fun favourite() = FavouriteItem(
        codeId = 1L,
        code = "E6",
        titleEn = "Compressor drive overcurrent",
        titleUr = "Compressor IPM fault",
        severity = "stop_pro",
        brandId = "dawlance",
        brandName = "Dawlance",
        seriesUid = "dawlance/inverter-split",
        seriesId = "inverter-split",
        seriesName = "Splits",
        createdAt = "2026-03-04T09:15:00Z",
    )

    private fun detail(sourceUrl: String?) = CodeDetail(
        summary = CodeSummary(
            id = 1L,
            uid = "x/y/E1",
            code = "E1",
            titleEn = "t",
            titleUr = "t",
            severity = "info",
            isFault = false,
            display = "unknown",
        ),
        meaningEn = null,
        meaningUr = null,
        notesEn = null,
        notesUr = null,
        confidence = "high",
        sourceType = "service_manual",
        sourceTitle = null,
        sourceUrl = sourceUrl,
        blinkPattern = null,
        relatedCodes = null,
        seriesId = "inverter-split",
        brandId = "dawlance",
        causes = emptyList(),
        solutions = emptyList(),
        isFavourite = false,
    )
}
