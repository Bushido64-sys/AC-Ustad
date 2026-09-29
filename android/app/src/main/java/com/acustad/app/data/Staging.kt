package com.acustad.app.data

/**
 * Whether the bundled database has to be copied into the cache directory again.
 *
 * Public, pure, and the entire decision, so that it can be tested without a device and read
 * without opening `KbDatabase`. It encodes a rule from
 * `PHASE_7_OFFLINE_AND_UPDATES.md` §2: **a data update ships as an ordinary new APK, and the app
 * must pick it up on launch.**
 *
 * Two different questions are being asked, and the bug this replaces asked the wrong one:
 *
 *  - **Has the cached copy been damaged?** A copy interrupted by a process kill is short, so
 *    comparing the file length catches it. That is what the old check was for, and it is good at
 *    it.
 *  - **Is the cached copy out of date?** An app update can ship a brand new database. If the new
 *    `kb.sqlite` happens to be *exactly the same number of bytes* as the old one, a length check
 *    alone says "nothing changed", the app never re-copies, and the technician keeps reading
 *    answers the developer already fixed. Silently, with nothing in a log.
 *
 * So the version is checked as well. The app cannot cheaply read `meta.kb_version` **out of the
 * asset** — an asset is not a file path, and extracting 8.8 MB to read one key on every launch
 * would be absurd — so the app's own `versionCode` is the signal. That is sound because the data
 * lives *inside* the APK: new data means a new APK, and `PHASE_7` §2 step 2 already requires
 * `versionCode` to be bumped for a release. The cost of being wrong in the other direction is
 * that a code-only update re-copies an identical database once, which is a few hundred
 * milliseconds on the first launch after an update and is always safe.
 *
 * The one hole is a process failure rather than a code failure: shipping new data **without**
 * bumping `versionCode`. Nothing here can detect that, and pretending otherwise would be worse
 * than saying so.
 *
 * @param stampedVersion the `versionCode` recorded when the cache copy was last written, or null
 *   if there is no stamp — a fresh install, or a build from before the stamp existed. Null always
 *   re-copies, because an unstamped cache cannot be shown to match this build.
 */
fun shouldRestage(
    cachedExists: Boolean,
    cachedLength: Long,
    assetLength: Long,
    stampedVersion: Int?,
    currentVersion: Int,
): Boolean = when {
    !cachedExists -> true
    // A short cache file is a copy interrupted by a kill. Length is the right question here.
    cachedLength != assetLength -> true
    // Unstamped: this build cannot claim the cache belongs to it.
    stampedVersion == null -> true
    // A different APK is installed. It may carry a different database, and re-copying an
    // identical one is cheap and harmless, so err towards copying.
    stampedVersion != currentVersion -> true
    else -> false
}
