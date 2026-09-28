package com.acustad.app.repo

/**
 * Ignores a repeat toggle of the same code inside a short window.
 *
 * The star is a write, and a write that lands twice is not a harmless no-op: the second
 * `INSERT`/`DELETE` reorders or re-stamps the saved list, and on a phone with gloves a
 * double tap is not a rare event. So a toggle on a code that was already toggled within
 * [windowMs] is dropped rather than applied. (PHASE_6_FAVOURITES.md §3)
 *
 * Deliberately pure and dependency-free:
 *
 *  - the clock is a **parameter**, not `SystemClock.elapsedRealtime()`, so the rule is
 *    testable on the JVM with no Robolectric and no `kotlinx-coroutines-test`;
 *  - the caller passes a monotonic reading. `System.currentTimeMillis()` must not be used —
 *    a clock that jumps backwards would make `now - previous` negative and lock the toggle
 *    out for as long as the jump is large.
 *
 * Entries are pruned whenever the map grows past [maxEntries] and an entry has aged out, so
 * it cannot accumulate across a long session. In practice it holds a handful of Longs: only a
 * code the user actually touched is ever recorded.
 */
class ToggleGuard(
    private val windowMs: Long = WINDOW_MS,
    private val maxEntries: Int = MAX_ENTRIES,
) {
    private val lastAt = HashMap<Long, Long>()

    /** True when this toggle may be applied. A denied toggle is not recorded as the new time. */
    fun allow(codeId: Long, nowMs: Long): Boolean {
        val previous = lastAt[codeId]
        if (previous != null && nowMs - previous < windowMs) return false
        lastAt[codeId] = nowMs
        prune(nowMs)
        return true
    }

    private fun prune(nowMs: Long) {
        if (lastAt.size <= maxEntries) return
        val expired = lastAt.entries.filter { nowMs - it.value >= windowMs }
        expired.forEach { lastAt.remove(it.key) }
    }

    companion object {
        /** Long enough to swallow a double tap, short enough that a deliberate re-toggle works. */
        const val WINDOW_MS = 400L
        private const val MAX_ENTRIES = 32
    }
}
