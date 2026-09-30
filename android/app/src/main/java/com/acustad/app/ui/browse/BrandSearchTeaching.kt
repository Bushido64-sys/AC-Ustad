package com.acustad.app.ui.browse

/**
 * The teaching empty state, as a decision rather than a string.
 *
 * Typing a code on the **brands** screen finds nothing, and the naive response — "No brand
 * matches E6" — leaves a technician staring at a search box that looks broken. The rule behind it
 * is the app's central design decision (RULE 3, RULE 2), so the screen states it once, in plain
 * terms, with somewhere to go next. DESIGN.md §4.2:
 *
 * > *"Codes are searched inside a model, because E6 means something different on 16 brands.
 * > This turns the rule into a feature instead of a dead end."*
 *
 * ### Why this is a sealed type and not a string
 *
 * Four different situations are all "no results", and collapsing them into one message is the
 * bug this avoids:
 *
 *  - the query is a code that exists on several brands → explain the rule, with a count;
 *  - the query is a code on exactly **one** brand → "different on 1 brands" is absurd, and the
 *    reason is different: they were nearly right, and going one level down is the fix;
 *  - the query is a **description of a fault** — `air leakage`, `not cooling` → nothing here is
 *    wrong with what they typed, and this box is simply the wrong box: brand names are all it
 *    searches (DESIGN.md §4.2, RULE 3). Silence would read as a broken search;
 *  - the query is a **brand** that does not exist → there is nothing to teach. The number would
 *    be 0, "E6 means something different on 0 brands" is nonsense, and a paragraph of
 *    explanation would be noise on a typo.
 *
 * Only a model that separates these can say the right thing, and only a model that separates
 * them is testable off a device. This is pure: no Compose, no database, no Android.
 *
 * ### The brand count is a fact about the data
 *
 * It comes from `SearchDao.brandsWithCode` and is never written down in this app. The guide's
 * "16 brands" is an illustration of the shape of the sentence, not a value to reproduce: `E6`
 * is on 16 brands today and on a different number after the next data release, and a hard-coded
 * figure would be the exact stale-claim failure this project keeps producing.
 */
sealed interface BrandSearchTeaching {

    /** Nothing to teach. The query was a brand name, or a code no brand publishes. */
    data object None : BrandSearchTeaching

    /**
     * The query is a code published by more than one brand, which is precisely why it cannot be
     * answered from this screen.
     *
     * @param code the technician's own text, not a re-cased version of it. They typed `e6` and
     *   the message says `e6` back to them; correcting their casing teaches nothing and reads
     *   as condescending.
     * @param brandCount how many brands publish it. Always greater than one — see
     *   [OneBrand].
     */
    data class Ambiguous(val code: String, val brandCount: Int) : BrandSearchTeaching

    /**
     * The query is a code, and exactly one brand publishes it.
     *
     * Worth its own state because the sentence changes completely. "Different on 1 brands" is
     * not a number a person can read, and the useful information is different too: this code
     * *does* exist, on one machine, so the way forward is to go one level down rather than to
     * question the search.
     */
    data class OneBrand(val code: String, val brandName: String) : BrandSearchTeaching

    /**
     * The query is a phrase describing a fault — `air leakage`, `not cooling` — and this box
     * searches brand names.
     *
     * Worth its own state because the reason is entirely different from the other three:
     * nothing is wrong with what they typed, and nothing is wrong with the search. The box is
     * simply one level too high. `PHASE_5_SEARCH.md` §1 puts it as a table row — "Free text in
     * a model | that series' descriptions" — and a technician who reads a dead "No brand
     * matches air leakage" has no way to discover that row.
     *
     * @param words the technician's own text, trimmed, casing untouched — see [Ambiguous].
     */
    data class Description(val words: String) : BrandSearchTeaching
}

/**
 * Decides which of those four situations a dead-end query is.
 *
 * @param query what the technician typed, verbatim.
 * @param brandCount how many brands publish the code, from
 *   [com.acustad.app.repo.KbRepository.brandsWithCode]. Zero means "not a code at all".
 * @param brandName the name of the single publishing brand, when it is knowable. Passed in
 *   rather than looked up so this function stays pure; the caller resolves it.
 *
 * ### The digit test, and why it is not a guess
 *
 * The rule is: **a query containing a digit is probably a code.** Measured against the shipped
 * database, not guessed:
 *
 *  - **0 of the 62 brand names contain a digit.** Not one. So the test cannot misfire on a real
 *    brand name.
 *  - **1,571 of the 2,139 distinct code strings (73%) contain a digit** — `E6`, `F4`, `ID013`,
 *    `P003`, `Error 200`, and the blink patterns like `LED1 x1 blink; LED2 off`.
 *
 * A name-with-no-digit is therefore treated as a brand search and gets a plain "no match". The
 * reverse bias is deliberate: when in doubt, say nothing extra. A paragraph of explanation
 * dropped on someone who mistyped a brand name is worse than no paragraph.
 *
 * ### The one kind of query where saying something is right
 *
 * [isDescription] carves a single exception: a **multi-word** phrase with no digit. The bias
 * above still holds for `sharpe` or `carrierr` — those are one word, and a typo is still a
 * typo. Two words with no digit between them are not how any of the 62 brand names is spelled,
 * so when the list is empty the technician did not mistype a brand: they described a fault, and
 * the honest answer is to say which box takes those.
 *
 * The cost is a two-word brand typo getting the same line. It is accepted, because the
 * headline stays "No brand matches" in every branch — the teaching line is a `detail` under it,
 * not a replacement — and the sentence it adds is true for a typo too.
 *
 * Total by construction — it runs on every keystroke and must never throw.
 */
fun teachingFor(query: String, brandCount: Int, brandName: String? = null): BrandSearchTeaching {
    val trimmed = query.trim()
    if (trimmed.isEmpty()) return BrandSearchTeaching.None
    if (brandCount <= 0) {
        // Nothing publishes it, so this is not a code — unless it never was one. A description
        // is the only other thing a dead-end query here can be, and it has its own sentence.
        return if (isDescription(trimmed)) {
            BrandSearchTeaching.Description(trimmed)
        } else {
            BrandSearchTeaching.None
        }
    }
    if (!trimmed.any { it.isDigit() }) return BrandSearchTeaching.None

    return when {
        brandCount > 1 -> BrandSearchTeaching.Ambiguous(trimmed, brandCount)
        brandName != null && brandName.isNotBlank() ->
            BrandSearchTeaching.OneBrand(trimmed, brandName.trim())
        // One brand, but we could not name it. Still better than the ambiguous sentence, and
        // better than silence: the code does exist, one level down.
        else -> BrandSearchTeaching.OneBrand(trimmed, "")
    }
}

/**
 * Whether a dead-end query is a **phrase describing a fault** rather than a brand name or a
 * code. The reason this is a separate function, rather than a line inside [teachingFor]: the
 * view model needs it before it decides whether a database lookup is worth running at all.
 *
 * Two words and no digit. Both halves are load-bearing:
 *
 *  - **the digit** keeps `Error 200` and `LED1 x1 blink` on the code path, where they belong;
 *  - **the space** is what stops a one-word typo (`sharpe`) from earning a paragraph, which is
 *    the failure the rest of this file is built around.
 *
 * A brand name with a space in it (`Blue Star`) is not a special case: if it matched, the list
 * would not be empty and nothing here would be shown.
 */
fun isDescription(query: String): Boolean {
    val trimmed = query.trim()
    return trimmed.isNotEmpty() &&
        trimmed.any { it.isWhitespace() } &&
        !trimmed.any { it.isDigit() }
}

/**
 * Whether the teaching state is even worth attempting for this query.
 *
 * Split out from [teachingFor] so the *cheap* half — "does this contain a digit" — can run on
 * every keystroke without a query, and the database is only touched when there is a reason to.
 * The canonical form itself is applied by the DAO, so this deliberately works on the raw text:
 * a technician's `e6` and `E6` are the same query to them, and the digit survives both.
 */
fun looksLikeACode(query: String): Boolean = query.isNotBlank() && query.any { it.isDigit() }
