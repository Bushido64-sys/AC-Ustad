package com.acustad.app.data

/**
 * Search input canonicalisation.
 *
 * ## The rule
 *
 * `canon()` is not a guess. It was derived by testing candidate rules against the shipped
 * database until one reproduced `aliases.alias_norm` for **all 4,124 distinct alias pairs**,
 * after which **all 2,139 distinct code strings resolve** through the `aliases` table.
 *
 * ```kotlin
 * canon(s) = UPPER(s), keeping A-Z 0-9 _ . / -,
 *            every other run of characters collapsed to a single space,
 *            runs of whitespace collapsed, then trimmed
 * ```
 *
 * ## Why UPPER and not lower
 *
 * `alias_norm` in the shipped database is stored **UPPER-CASED**, not lower-cased: 5,562 of
 * its 7,707 rows contain capitals (`E1`, `HIGH TEMP`, `BLINK-RUNNING`, `ISO_FAIL`). An
 * earlier version of this file lower-cased the user's query before matching, which is the
 * intuitive choice and the wrong one — measured against the real database:
 *
 * | query  | lower-cased match | canonical match |
 * |--------|-------------------|-----------------|
 * | `E1`   | 31 rows           | 31 rows         |
 * | `e1`   | **0 rows**        | 31 rows         |
 * | `E6`   | 26 rows           | 26 rows         |
 * | `e6`   | **0 rows**        | 26 rows         |
 *
 * A technician typing `e1` would have found nothing at all. Canonicalising to upper case is
 * both correct AND faster, because it can use the `idx_alias_norm` index instead of forcing
 * a full scan with `LOWER()` (0.15 ms vs 3.6 ms).
 *
 * ## Where it is applied
 *
 * To the user's query ONLY. The stored `alias_norm` values are already canonical.
 */
object SearchInput {

    /** Anything that is not a letter, digit or one of `_ . / -` becomes a single space. */
    private val NON_CANON = Regex("[^A-Z0-9_./\\-]+")
    private val WHITESPACE = Regex("\\s+")

    /**
     * Canonicalise a query for an exact or prefix `alias_norm` match.
     *
     * Total function: it must never throw, because it runs on every keystroke.
     */
    fun canon(raw: String): String = raw
        .uppercase()
        .replace(NON_CANON, " ")
        .replace(WHITESPACE, " ")
        .trim()

    /**
     * Builds a safe FTS5 `MATCH` expression from free text. Two things are going on, and both
     * are load-bearing.
     *
     * **1. Every term is quoted individually.** Not optional decoration. 454 of the 2,139 code
     * strings throw if passed raw — `BLINK-RUNNING` becomes `no such column: RUNNING`, and
     * anything containing `;` or `+` is a syntax error. Quoted, all 2,139 return results.
     *
     * **2. The terms are AND-joined, not wrapped as one quoted phrase.** A single quoted string
     * is an FTS5 *phrase*: the words must be adjacent and in that order. Measured on the shipped
     * database, `"over current"` returns 110 hits while `"over" AND "current"` returns 133 — the
     * difference is real codes whose wording puts the words in another order, or in a different
     * field. A technician types words, not phrases, so demanding adjacency silently hides answers
     * that are sitting right there. Quoting each term keeps the crash protection; dropping the
     * adjacency requirement is what makes the search forgiving.
     */
    fun ftsQuery(raw: String): String =
        raw.split(' ', '\t', '\n')
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .joinToString(" AND ") { "\"" + it.replace("\"", "\"\"") + "\"" }

    /**
     * True when a query looks like a code rather than a description: short and space-free
     * once canonicalised. Decides whether to try the `aliases` table first.
     */
    fun looksLikeCode(query: String): Boolean =
        query.isNotEmpty() && query.length <= MAX_CODE_LENGTH && !query.contains(' ')

    /** Longest canonical query still treated as a code lookup rather than free text. */
    private const val MAX_CODE_LENGTH = 24
}
