package com.acustad.app

import android.app.Application
import android.os.StrictMode

/**
 * AC Ustad — AC & solar inverter error code knowledge base.
 *
 * The whole knowledge base is a read-only SQLite file bundled in `assets/db/kb.sqlite`.
 * There is no network client, no analytics and no background work in this app
 * (RULES.md RULE 14/15), so there is nothing to initialise here beyond making sure
 * the process does not die from lack of memory while the database is being read.
 *
 * Phase 9: StrictMode + Trace live here, debug builds only. Every database read goes
 * through `Io.io` on Dispatchers.IO; the one accepted exception is the first
 * `getSharedPreferences` read in KbRepository, which is tens of bytes once per process
 * and is recorded in that file's KDoc. A penaltyLog (never penaltyDeath) keeps this a
 * detector, not a crash source on a technician's phone.
 */
class UstadApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .build(),
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .build(),
            )
        }
        // Nothing to warm up: the database opens lazily on first read, off the main thread.
        // Kept intentionally un-preloaded so cold start is instant and
        // the home screen can show real counts on its first frame.
    }
}
