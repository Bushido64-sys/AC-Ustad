package com.acustad.app.ui.settings

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * The build-date formatter behind the Settings data block.
 *
 * It exists because the alternative is a claim about the data that nothing measured: a truncated
 * `built_at` states that the knowledge base was built on a day that may not be its build day.
 * (trap 5)
 *
 * The size formatter that used to sit here went with the line that used it, 2026-09-29 — see the
 * About panel. Removing a function and its tests is the correct way to delete a feature; leaving
 * the tests would not compile and keeping the function "just in case" is the dead code this
 * project sweeps for.
 */
class SettingsFormatTest {

    @Test
    fun `an ISO timestamp is reduced to its date`() {
        assertEquals("2026-09-28", formatBuiltDate("2026-09-28T05:48:34Z"))
    }

    @Test
    fun `a value with no date in it is null, not a truncated string`() {
        // The "unknown" fallback in `CatalogDao.meta()`, an empty column, and anything a future
        // data release might write. All of them omit the line instead of showing a fragment.
        assertNull(formatBuiltDate("unknown"))
        assertNull(formatBuiltDate(""))
        assertNull(formatBuiltDate("   "))
        assertNull(formatBuiltDate(null))
    }

    @Test
    fun `a bare date is passed through unchanged`() {
        assertEquals("2026-09-26", formatBuiltDate("2026-09-26"))
    }

    @Test
    fun `surrounding whitespace is trimmed before the date is taken`() {
        assertEquals("2026-09-28", formatBuiltDate("  2026-09-28T05:48:34Z  "))
    }
}
