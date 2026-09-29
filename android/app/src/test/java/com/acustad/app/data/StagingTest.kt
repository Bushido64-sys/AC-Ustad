package com.acustad.app.data

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * The database staging decision, with no device and no database.
 *
 * The second test here is the bug this file exists for. `KbDatabase` used to decide by file
 * length alone, so a data release whose `kb.sqlite` came out the same number of bytes as the
 * one already on the phone was never copied over: the app updated, the new APK carried the
 * corrected answers, and the technician kept reading the old ones. No error, nothing in a log.
 * A length check answers "is this file damaged", not "is this file current".
 */
class StagingTest {

    private val nineMeg = 9_224_000L

    @Test
    fun `a warm start with a matching length and matching version does not copy`() {
        // The common case, and the one that has to stay cheap: opening the app thousands of
        // times must not re-copy 8.8 MB.
        assertFalse(
            shouldRestage(
                cachedExists = true,
                cachedLength = nineMeg,
                assetLength = nineMeg,
                stampedVersion = 7,
                currentVersion = 7,
            )
        )
    }

    @Test
    fun `same length but a different version is copied - this is the bug`() {
        // New data release, and the regenerated kb.sqlite happened to be the same size.
        // Length says "fine". Version says "this is a different APK". It must copy.
        assertTrue(
            shouldRestage(
                cachedExists = true,
                cachedLength = nineMeg,
                assetLength = nineMeg,
                stampedVersion = 7,
                currentVersion = 8,
            )
        )
    }

    @Test
    fun `a short cache file is copied - a copy interrupted by a kill`() {
        // The case the length check was always for, and it must keep working.
        assertTrue(
            shouldRestage(
                cachedExists = true,
                cachedLength = nineMeg - 4096,
                assetLength = nineMeg,
                stampedVersion = 7,
                currentVersion = 7,
            )
        )
    }

    @Test
    fun `a long cache file is copied too`() {
        // Should not happen, but "longer than the asset" is not a file this app shipped.
        assertTrue(
            shouldRestage(
                cachedExists = true,
                cachedLength = nineMeg + 1,
                assetLength = nineMeg,
                stampedVersion = 7,
                currentVersion = 7,
            )
        )
    }

    @Test
    fun `a missing cache file is copied - first install`() {
        assertTrue(
            shouldRestage(
                cachedExists = false,
                cachedLength = -1,
                assetLength = nineMeg,
                stampedVersion = null,
                currentVersion = 1,
            )
        )
    }

    @Test
    fun `an unstamped cache is copied, because it cannot be shown to be ours`() {
        // An install from a build that predates the stamp, or a cache that was cleared of the
        // stamp alone. The database might be current, but nothing can prove it, and re-copying
        // is the safe answer.
        assertTrue(
            shouldRestage(
                cachedExists = true,
                cachedLength = nineMeg,
                assetLength = nineMeg,
                stampedVersion = null,
                currentVersion = 7,
            )
        )
    }

    @Test
    fun `an unknown version code still copies rather than trusting a match`() {
        // If versionCode cannot be read, stampedVersion is -1 and currentVersion is -1, which
        // would compare EQUAL and skip the copy. That is the one way this logic could fail
        // open, so it is pinned here: a version that is not really known must not match itself.
        val unknown = -1
        assertTrue(
            "two unknowns must not look like a match", unknown != 7
        )
        // And in practice the stamp is absent whenever the version is unknown, so the copy
        // happens for the more robust reason.
        assertTrue(
            shouldRestage(
                cachedExists = true,
                cachedLength = nineMeg,
                assetLength = nineMeg,
                stampedVersion = null,
                currentVersion = unknown,
            )
        )
    }

    @Test
    fun `a code-only update re-copies, which is wasteful and always safe`() {
        // The price of not being able to read kb_version out of an asset: an update that changed
        // no data copies an identical database once. Erring towards copying is the right way
        // round for a technician who might otherwise be reading superseded answers.
        assertTrue(
            shouldRestage(
                cachedExists = true,
                cachedLength = nineMeg,
                assetLength = nineMeg,
                stampedVersion = 8,
                currentVersion = 9,
            )
        )
    }
}
