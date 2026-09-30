package com.acustad.app.ui.browse

import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

/**
 * What the codes screen says when a search returns nothing.
 *
 * The description step of `SearchDao.searchCodes` leaves no trace of itself: it either adds
 * rows or adds nothing, and on the brands screen a fault phrase at least gets the teaching
 * line (`BrandSearchTeaching`). A technician who searched a model and saw a bare
 * "No code matches" reasonably concluded the description search was never implemented there —
 * it was, invisibly. This pins the wiring a green build cannot see (trap 15): the screen must
 * hand its description-shaped queries the scope detail, and the resource must exist with the
 * model name in it.
 *
 * Same brace-counting anchor as `RowCountLabelTest` for the same reason.
 */
class CodesEmptyStateTest {

    private val screen = File("src/main/java/com/acustad/app/ui/browse/CodesScreen.kt").readText()
    private val strings = File("src/main/res/values/strings.xml").readText()

    @Test
    fun `a fault description gets the scope detail, a code does not`() {
        assertTrue(
            "CodesScreen must test the query with isDescription, or every dead search looks " +
                "identical and the description step stays invisible",
            screen.contains("isDescription(query)"),
        )
        assertTrue(
            "CodesScreen must pass empty_no_code_match_detail down to the empty state",
            screen.contains("R.string.empty_no_code_match_detail"),
        )
        assertTrue(
            "the detail must be handed to EmptyState as detail, not shown as a second message",
            screen.contains("EmptyState(message = noMatch, detail = detail)"),
        )
    }

    @Test
    fun `the detail names the model it searched`() {
        val start = strings.indexOf("name=\"empty_no_code_match_detail\"")
        assertTrue("empty_no_code_match_detail is missing from strings.xml", start >= 0)
        val block = strings.substring(start).substringBefore("</string>")
        assertTrue(
            "the detail must interpolate the model name, or it cannot say where it looked",
            block.contains("%1\$s"),
        )
    }
}
