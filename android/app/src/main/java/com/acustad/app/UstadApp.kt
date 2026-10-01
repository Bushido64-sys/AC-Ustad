package com.acustad.app

import android.app.Application
import android.os.StrictMode
import com.acustad.app.ads.AdsManager
import com.acustad.app.ads.AppOpenManager

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
        //
        // Ads initialise here and not in MainActivity, because the first
        // foreground transition fires before any consent callback returns —
        // initialising late is exactly why the first build showed no ad on
        // startup. Consent still runs in MainActivity and gates personalised
        // loads from there; the GMA SDK honours the UMP state for everything
        // loaded after it. (ADS.md)
        runCatching {
            AdsManager.init(this)
            AdsManager.preloadAppOpen(this)
        }
        //
        // App Open lives here because it is process-scoped, not screen-scoped:
        // every foreground transition arrives as lifecycle callbacks whether
        // any screen asked for an ad or not. (ADS.md)
        runCatching {
            AppOpenManager(this).register()
        }
    }
}
