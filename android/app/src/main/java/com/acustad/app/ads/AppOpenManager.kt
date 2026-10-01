package com.acustad.app.ads

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * Shows the app-open ad whenever the app comes to the foreground.
 *
 * Cold start and long-background return both arrive here as a 0 → 1
 * transition on the started-activity count, so one callback covers both
 * without any timer of our own. Detail screens, dialogs and the consent
 * form are activity callbacks too, but they never move the count through
 * zero — only a real backgrounding does.
 *
 * Registered in `UstadApp.onCreate`. [AdsManager] refuses while another
 * full-screen ad is showing, so this can never stack on an interstitial
 * or a rewarded ad.
 */
class AppOpenManager(private val app: Application) : Application.ActivityLifecycleCallbacks {

    private var startedCount = 0

    fun register() {
        app.registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityStarted(activity: Activity) {
        if (startedCount == 0) {
            AdsManager.showAppOpenIfReady(activity)
        }
        startedCount++
    }

    override fun onActivityStopped(activity: Activity) {
        startedCount = maxOf(0, startedCount - 1)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) = Unit

    override fun onActivityResumed(activity: Activity) = Unit

    override fun onActivityPaused(activity: Activity) = Unit

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit

    override fun onActivityDestroyed(activity: Activity) = Unit
}
