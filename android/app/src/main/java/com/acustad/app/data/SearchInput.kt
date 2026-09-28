package com.acustad.app.data

/**
 * Search input normalisation.
 *
 * `aliases.alias_norm` is already normalised in the shipped database, so this function is
 * applied to the USER'S QUERY ONLY. Getting that backwards silently breaks every code
 * lookup, which is why it lives in one obvious place with a test beside it.
 *
 * Kept deliberately simple and total: it must never throw, whatever the user types, because
 * it runs on every keystroke of a search field.
 */
object SearchInput {

    private val UNSAFE = Regex("[^a-z0-9 +./-]")
    private val WHITESPACE = Regex("\\s+")

    /**
     * Lower-cases, strips characters that cannot appear in a normalised code, collapses
     * whitespace, and trims. Returns "" for input that contains nothing searchable.
     */
    fun normalise(raw: String): String = raw
        .lowercase()
        .replace(UNSAFE, "")
        .replace(WHITESPACE, " ")
        .trim()

    /**
     * Wraps free text in double quotes for an FTS5 MATCH.
     *
     * This is not optional decoration. 454 of the 2,139 distinct code strings throw if passed
     * raw, e.g. `BLINK-RUNNING` becomes `no such column: RUNNING`, and anything containing
     * `;` or `+` is a syntax error. Quoting makes all 2,139 return results.
     * See PHASE_5_SEARCH.md §3 and DATA_SCHEMA.md §7.
     */
    fun ftsQuery(raw: String): String = "\"" + raw.replace("\"", "\"\"") + "\""

    /**
     * True when a query looks like a code rather than a description: short, no spaces.
     * Used to decide whether to go to the `aliases` table first.
     */
    fun looksLikeCode(query: String): Boolean =
        query.isNotEmpty() && query.length <= MAX_CODE_LENGTH && !query.contains(' ')

    /** Longest normalised query still treated as a code lookup rather than free text. */
    private const val MAX_CODE_LENGTH = 24
}
