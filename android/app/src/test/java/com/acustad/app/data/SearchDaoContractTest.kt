package com.acustad.app.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

/**
 * The description search's contract, pinned as source text.
 *
 * Same admission as [com.acustad.app.ui.common.ContainerContentColorTest]: there is no SDK and
 * no emulator on the build machine, so a JVM test cannot open a `Cursor`. What it can see is the
 * exact text of the query — and for a query, that is the part that breaks: `check_app_sql.py`
 * documents a `DETAIL_SQL` that read one index off a 20-column cursor, after which every code in
 * the app rendered "This code is not in the knowledge base" instead of crashing.
 *
 * The behaviour of this query against the shipped database is asserted separately, where it can
 * actually be run: `app-pipeline/check_app_sql.py` extracts [SearchDao.DESCRIPTION_SQL] from this
 * file and executes it in CI. This file pins what that checker cannot see — that the projection
 * matches what [toSummary] reads, and that the query is only reachable as the last step of a
 * search.
 */
class SearchDaoContractTest {

    private val source = File("src/main/java/com/acustad/app/data/SearchDao.kt").readText()
    private val codeDao = File("src/main/java/com/acustad/app/data/CodeDao.kt").readText()

    /** Columns 0–7, in the order `Cursor.toSummary()` reads them by index. */
    private val summary = listOf(
        "id", "uid", "code", "title_en", "title_ur", "severity", "is_fault", "display",
    )

    @Test
    fun `the description query projects the summary columns in the order toSummary reads them`() {
        assertEquals(summary, projection(descriptionSql()))
    }

    @Test
    fun `every query in SearchDao projects the same eight columns the same way`() {
        // The new query is checked against the three that already ship rather than against a
        // list this test invented, so one of them drifting fails here too and names the file.
        for (anchor in listOf("codesExact", "codesPrefix", "codesText")) {
            assertEquals(
                "the projection of $anchor drifted from the summary column order",
                summary,
                projection(bodyOf(source, anchor)),
            )
        }
    }

    @Test
    fun `toSummary still reads those columns by index, which is what makes the order load-bearing`() {
        // If this ever moves to reading by name (as `col()` does), the order above stops being
        // load-bearing and these checks can be simplified rather than left to rot.
        assertTrue(
            "CodeDao.toSummary no longer reads by index, so SearchDaoContractTest's projection " +
                "checks are guarding a rule that no longer applies",
            codeDao.contains("id = getLong(0)") && codeDao.contains("display = getString(7)"),
        )
    }

    @Test
    fun `the description query binds both halves of the scope, in the query and not at the call site`() {
        // series_id alone is not unique: 6 values repeat, one 14 times. A query bound on the
        // series only would return another brand's codes for those six — RULE 3, the app's
        // central rule, broken by one missing line.
        val sql = descriptionSql()
        assertTrue(
            "DESCRIPTION_SQL must bind c.series_id = ?, or a description search can leave the " +
                "model line",
            sql.contains("c.series_id = ?"),
        )
        assertTrue(
            "DESCRIPTION_SQL must bind c.brand_id = ?, because series_id is not unique on its own",
            sql.contains("c.brand_id = ?"),
        )
        assertTrue(
            "the scope belongs to the statement, so the constant must carry a WHERE with the " +
                "two bindings before any term is substituted",
            sql.indexOf("c.series_id = ?") < sql.indexOf("%WHERE%"),
        )
    }

    @Test
    fun `the term predicates are substituted into the statement, never written by hand`() {
        // One `LIKE ?` per term, built in one place from one placeholder: a hand-written
        // predicate would be one term, always, and the query would answer "air leakage" with
        // whatever the first word matches.
        val sql = descriptionSql()
        assertTrue("DESCRIPTION_SQL lost its term placeholder", sql.contains("%WHERE%"))
        assertFalse(
            "the constant must not hard-code a term predicate; codesDescription builds them " +
                "from the term list",
            sql.contains("d.haystack LIKE ?"),
        )
        assertTrue(
            "codesDescription must substitute the placeholder, or the SQL never becomes runnable",
            bodyOf(source, "codesDescription").contains("DESCRIPTION_SQL.replace(WHERE_HOLDER"),
        )
    }

    @Test
    fun `the description query has exactly the three placeholders that are not terms`() {
        // Two for the scope, one for the LIMIT. One `LIKE ?` is appended per term at runtime, so
        // a count here only makes sense before that substitution: if a fourth hard-coded `?`
        // appears, the argument list built in codesDescription binds the limit to a term and the
        // query returns the wrong number of rows instead of failing.
        assertEquals(3, Regex("\\?").findAll(descriptionSql()).count())
    }

    @Test
    fun `the precise steps run in order, and the loose title search is last`() {
        val body = bodyOf(source, "searchCodes")
        val text = body.indexOf("codesText(")
        val known = body.indexOf("isKnownCode(")
        val description = body.indexOf("codesDescription(")
        val loose = body.indexOf("ftsQueryAny(")

        assertTrue("searchCodes no longer calls codesText", text >= 0)
        assertTrue("searchCodes no longer gates on isKnownCode", known >= 0)
        assertTrue("searchCodes no longer falls back to codesDescription", description >= 0)
        assertTrue("searchCodes no longer loosens the index with ftsQueryAny", loose >= 0)

        assertTrue(
            "the description search must come after the free-text index, or every query that " +
                "already works would pay for a second scan",
            text < description,
        )
        assertTrue(
            "a code that exists somewhere in the knowledge base must be rejected before the " +
                "description search runs, or a code this model is missing gets a list of codes " +
                "that merely mention it instead of the screen's 'no code matches' answer",
            known < description,
        )
        assertTrue(
            "the loose OR search must come after the description search, or titles that merely " +
                "share one word would outrank the answers the words together actually found",
            description < loose,
        )
        assertTrue(
            "the loose search must be the last codesText call in searchCodes, so the least " +
                "precise step can never return first",
            body.lastIndexOf("codesText(") > loose,
        )
        assertTrue(
            "a single word must never reach the loose search: it has nothing to loosen, and the " +
                "plain free-text step already asked the index for it",
            body.contains("loose.isEmpty()"),
        )
    }

    @Test
    fun `the description search cannot run with no terms`() {
        // An empty term list would otherwise build `WHERE ` with nothing after it — a syntax
        // error thrown out of a search box.
        assertTrue(
            "codesDescription must return early when the term list is empty",
            bodyOf(source, "codesDescription").contains("if (terms.isEmpty()) return emptyList()"),
        )
    }

    /** The `DESCRIPTION_SQL` constant, exactly as it is written in SearchDao.kt. */
    private fun descriptionSql(): String {
        val marker = "DESCRIPTION_SQL = \"\"\""
        val start = source.indexOf(marker)
        assertTrue("DESCRIPTION_SQL not found - the test anchor has drifted", start >= 0)
        val bodyStart = start + marker.length
        val end = source.indexOf("\"\"\"", bodyStart)
        assertTrue("DESCRIPTION_SQL is never closed", end > bodyStart)
        val raw = source.substring(bodyStart, end)
        val lines = raw.split('\n')
        val indent = lines.filter { it.isNotBlank() }.minOf { it.length - it.trimStart().length }
        return lines.joinToString("\n") { it.drop(indent) }.trim()
    }

    /**
     * The columns a query projects, taken from its **last** `SELECT` — which for
     * [SearchDao.DESCRIPTION_SQL] is the one after the CTE, and for the others the only one —
     * with any table alias stripped so `c.id` and `d.id` compare equal.
     */
    private fun projection(sql: String): List<String> =
        sql.split("SELECT").last()
            .substringBefore("FROM")
            .split(',')
            .map { it.replace("DISTINCT", "").trim().substringAfterLast('.').trim() }
            .filter { it.isNotEmpty() }

    /**
     * The declaration of the named function through its closing brace, found by counting braces
     * rather than by slicing to the next declaration — the same helper shape as
     * `ContainerContentColorTest`, for the same reason: a comment or a string inside the body
     * can contain the tokens a naive slice would stop at.
     */
    private fun bodyOf(text: String, name: String): String {
        val start = text.indexOf("fun $name(")
        assertTrue("fun $name( is not in SearchDao.kt - the test anchor has drifted", start >= 0)
        var depth = 0
        var i = text.indexOf('{', start)
        assertTrue("fun $name( has no body in SearchDao.kt", i > start)
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
        assertTrue("fun $name( body never closes - SearchDao.kt is malformed", false)
        return ""
    }
}
