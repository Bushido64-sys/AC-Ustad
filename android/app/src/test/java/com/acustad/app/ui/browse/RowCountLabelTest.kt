package com.acustad.app.ui.browse

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

/**
 * Which count each list row puts under the name.
 *
 * A human found this wrong on a phone: the brand row read "41 codes" and the model row one
 * screen later read "41 models" — the two labels the wrong way round. Both are single numbers
 * in a right-aligned mono `Text`, so a green build says nothing about which string resource
 * they came from, and no test opened a screen (trap 15). This reads the source instead.
 *
 * The rule the swap encodes: **each row answers the question that screen is asking.** On the
 * brands screen the question is "how much is inside this brand?" → models. On the models
 * screen it is "how much is inside this model?" → codes.
 */
class RowCountLabelTest {

    private val brands = File("src/main/java/com/acustad/app/ui/browse/BrandsScreen.kt").readText()
    private val series = File("src/main/java/com/acustad/app/ui/browse/SeriesScreen.kt").readText()

    @Test
    fun `a brand row counts models, and reads them off the brand`() {
        val body = bodyOf(brands, "BrandRow")
        assertTrue(
            "BrandRow must render the model count (unit_series_*), or the brand row is back to " +
                "showing codes",
            body.contains("R.string.unit_series_one") && body.contains("R.string.unit_series_many"),
        )
        assertFalse(
            "BrandRow must not render unit_codes_*; that belongs to the model row one screen down",
            body.contains("R.string.unit_codes_one") || body.contains("R.string.unit_codes_many"),
        )
        assertTrue(
            "the value shown must be seriesCount, not the brand's code count",
            brands.contains("seriesCount = brand.seriesCount"),
        )
    }

    @Test
    fun `a model row counts codes`() {
        val body = bodyOf(series, "SeriesRow")
        assertTrue(
            "SeriesRow must render the code count (unit_codes_*); a row that is already a model " +
                "does not need to say how many models it is",
            body.contains("R.string.unit_codes_one") && body.contains("R.string.unit_codes_many"),
        )
        assertFalse(
            "SeriesRow must not render unit_series_*; that belongs to the brand row one screen up",
            body.contains("R.string.unit_series_one") || body.contains("R.string.unit_series_many"),
        )
        assertTrue(
            "the value shown must stay the series' own code count",
            body.contains("codeCount"),
        )
    }

    /**
     * The declaration of the named composable through its closing brace, found by counting braces
     * — the same helper as `SearchDaoContractTest`, for the same reason: a comment or a string
     * inside the body can contain the tokens a naive slice would stop at.
     */
    private fun bodyOf(text: String, name: String): String {
        val start = text.indexOf("fun $name(")
        assertTrue("fun $name( is not in its screen file - the test anchor has drifted", start >= 0)
        var depth = 0
        var i = text.indexOf('{', start)
        assertTrue("fun $name( has no body", i > start)
        while (i < text.length) {
            when (text[i]) {
                '{' -> depth++
                '}' -> {
                    depth--
                    if (depth == 0) return text.substring(start, i + 1)
                }
            }
            i++
        }
        assertTrue("fun $name( body never closes", false)
        return ""
    }
}
