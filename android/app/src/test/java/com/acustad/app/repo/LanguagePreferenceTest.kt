package com.acustad.app.repo

import com.acustad.app.model.ContentLanguage
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * The stored-value mapping for the content language.
 *
 * This is the one piece of the toggle that can be tested without a device, and it is the piece
 * that can brick a launch: it reads a preferences file written by a **previous version of the
 * app**. If that file holds a value this build does not recognise, the honest failure is "fall
 * back to English and carry on", not an exception on the first query of every session.
 */
class LanguagePreferenceTest {

    @Test
    fun `a stored UR comes back as Roman Urdu`() {
        assertEquals(ContentLanguage.UR, languageFrom("UR"))
    }

    @Test
    fun `a stored EN comes back as English`() {
        assertEquals(ContentLanguage.EN, languageFrom("EN"))
    }

    @Test
    fun `a missing value is English, which is the documented default`() {
        assertEquals(ContentLanguage.EN, languageFrom(null))
    }

    @Test
    fun `an unrecognised value is English rather than a crash`() {
        // This is the case `enumValueOf` would throw on. A future rename, a hand-edited
        // preferences file, or a value written by a build between the two must all open the app.
        assertEquals(ContentLanguage.EN, languageFrom("ur"))
        assertEquals(ContentLanguage.EN, languageFrom("ROMAN_URDU"))
        assertEquals(ContentLanguage.EN, languageFrom(""))
        assertEquals(ContentLanguage.EN, languageFrom("   "))
        assertEquals(ContentLanguage.EN, languageFrom("0"))
    }

    @Test
    fun `the round trip through the stored name is lossless`() {
        // The toggle writes `language.name`, so reading it back must be exact for both values.
        // A lossy round trip would silently reset a technician's choice on the next launch.
        for (language in ContentLanguage.entries) {
            assertEquals(language, languageFrom(language.name))
        }
    }

    @Test
    fun `English is the default, so a fresh install is English`() {
        // Fresh install means no preferences file at all, which is the same as a null value.
        assertEquals(ContentLanguage.EN, languageFrom(null))
        assertEquals(ContentLanguage.EN, ContentLanguage.entries.first())
    }
}
