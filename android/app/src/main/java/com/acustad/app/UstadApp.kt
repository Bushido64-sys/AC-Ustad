package com.acustad.app

import android.app.Application

/**
 * AC Ustad — AC & solar inverter error code knowledge base.
 *
 * The whole knowledge base is a read-only SQLite file bundled in `assets/db/kb.sqlite`.
 * There is no network client, no analytics and no background work in this app
 * (RULES.md RULE 14/15), so there is nothing to initialise here beyond making sure
 * the process does not die from lack of memory while the database is being read.
 */
class UstadApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // Nothing to warm up: the database opens lazily on first read, off the main thread.
        // Kept intentionally empty rather than "preloaded" so cold start is instant and
        // the home screen can show real counts on its first frame.
    }
}
