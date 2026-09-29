package com.acustad.app.repo

import com.acustad.app.model.ThemeMode
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * The stored-value mapping for the theme, and the rule that resolves SYSTEM.
 *
 * Same contract as [LanguagePreferenceTest], and the same reason it exists: this reads a
 * preferences file written by a **previous version of the app**. If that file holds a value this
 * build does not recognise, the honest failure is "fall back and carry on", not an exception on
 * the first composition of every session.
 */
class ThemePreferenceTest {

    @Test
    fun `each stored name comes back as its own mode`() {
        assertEquals(ThemeMode.SYSTEM, themeFrom("SYSTEM"))
        assertEquals(ThemeMode.LIGHT, themeFrom("LIGHT"))
        assertEquals(ThemeMode.DARK, themeFrom("DARK"))
    }

    @Test
    fun `a missing value follows the system, which is the documented default`() {
        assertEquals(ThemeMode.SYSTEM, themeFrom(null))
    }

    @Test
    fun `an unrecognised value follows the system rather than crashing`() {
        // The case `enumValueOf` would throw on. A future rename, a hand-edited preferences
        // file, or a value written by an older build must all open the app.
        assertEquals(ThemeMode.SYSTEM, themeFrom("dark"))
        assertEquals(ThemeMode.SYSTEM, themeFrom("NIGHT"))
        assertEquals(ThemeMode.SYSTEM, themeFrom(""))
        assertEquals(ThemeMode.SYSTEM, themeFrom("   "))
        assertEquals(ThemeMode.SYSTEM, themeFrom("2"))
    }

    @Test
    fun `an unrecognised value falls back to the system, not to light`() {
        // The subtle one. LIGHT and SYSTEM are the same value on a phone currently in light
        // mode, so a test that only checks the rendered app would pass either way. They are not
        // the same *decision*: only SYSTEM defers to the phone. Defaulting to LIGHT here would
        // let a preferences file silently override the user's own system setting.
        assertEquals(ThemeMode.SYSTEM, themeFrom("something-a-future-build-wrote"))
    }

    @Test
    fun `the round trip through the stored name is lossless`() {
        // The setter writes `mode.name`, so reading it back must be exact for all three. A lossy
        // round trip would reset the choice on the next launch, which is the bug this whole
        // mechanism exists to avoid.
        for (mode in ThemeMode.entries) {
            assertEquals(mode, themeFrom(mode.name))
        }
    }

    @Test
    fun `an explicit choice overrides the system in both directions`() {
        assertTrue(ThemeMode.DARK.isDark(systemIsDark = false))
        assertFalse(ThemeMode.LIGHT.isDark(systemIsDark = true))
    }

    @Test
    fun `SYSTEM defers to the system, both ways`() {
        assertTrue(ThemeMode.SYSTEM.isDark(systemIsDark = true))
        assertFalse(ThemeMode.SYSTEM.isDark(systemIsDark = false))
    }
}
