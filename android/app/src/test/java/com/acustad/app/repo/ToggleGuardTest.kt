package com.acustad.app.repo

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * The double-tap guard, with no Android and no coroutines.
 *
 * The clock is a parameter precisely so this runs on the JVM: a rule that can only be tested on
 * a device is a rule that will be broken. (PHASE_6_FAVOURITES.md §3)
 */
class ToggleGuardTest {

    @Test
    fun `the first toggle of a code is allowed`() {
        assertTrue(ToggleGuard().allow(codeId = 1L, nowMs = 0L))
    }

    @Test
    fun `a repeat toggle inside the window is dropped`() {
        val guard = ToggleGuard()
        assertTrue(guard.allow(1L, nowMs = 1_000L))
        // A second tap 200 ms later is a double tap, not an intent.
        assertFalse(guard.allow(1L, nowMs = 1_200L))
        assertFalse(guard.allow(1L, nowMs = 1_399L))
    }

    @Test
    fun `a toggle after the window is allowed again`() {
        val guard = ToggleGuard()
        assertTrue(guard.allow(1L, nowMs = 1_000L))
        // Exactly on the boundary, which is why the comparison is >= and not >.
        assertTrue(guard.allow(1L, nowMs = 1_400L))
        assertTrue(guard.allow(1L, nowMs = 5_000L))
    }

    @Test
    fun `a dropped tap does not extend the window`() {
        val guard = ToggleGuard()
        assertTrue(guard.allow(1L, nowMs = 0L))
        assertFalse(guard.allow(1L, nowMs = 100L))
        assertFalse(guard.allow(1L, nowMs = 300L))
        // If the rejected taps had refreshed the timestamp, this would still be locked out.
        // It must not be: 400 ms after the *accepted* tap, the user may tap again.
        assertTrue(guard.allow(1L, nowMs = 400L))
    }

    @Test
    fun `the guard is per code, not global`() {
        val guard = ToggleGuard()
        assertTrue(guard.allow(1L, nowMs = 1_000L))
        // Unrelated codes must not be blocked by an unrelated double tap.
        assertTrue(guard.allow(2L, nowMs = 1_010L))
        assertFalse(guard.allow(1L, nowMs = 1_020L))
    }

    @Test
    fun `the window is four hundred milliseconds`() {
        assertEquals(400L, ToggleGuard.WINDOW_MS)
        val guard = ToggleGuard()
        assertTrue(guard.allow(7L, nowMs = 10_000L))
        assertFalse(guard.allow(7L, nowMs = 10_399L))
        assertTrue(guard.allow(7L, nowMs = 10_400L))
    }
}
