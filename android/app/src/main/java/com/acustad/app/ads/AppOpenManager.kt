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

    /**
     * Whether an app-open ad already showed in the current foreground visit.
     * Without this, the resume retry below would stack a second ad on top of
     * the one the start transition just showed.
     */
    private var shownThisForeground = false

    /**
     * When the app last went to the background, 0 on a fresh process. A
     * rotation destroys and recreates the activity through a sub-second
     * backgrounding — without this floor, every rotation would pop an ad.
     */
    private var lastBackgroundAt = 0L

    fun register() {
        app.registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityStarted(activity: Activity) {
        if (startedCount == 0) {
            shownThisForeground = false
            if (isRealReturn()) {
                if (AdsManager.tryShowAppOpen(activity)) {
                    shownThisForeground = true
                }
            }
        }
        startedCount++
    }

    override fun onActivityResumed(activity: Activity) {
        // The cold-start second chance: the preload fired in UstadApp.onCreate
        // milliseconds before the first start, so the ad is routinely still
        // loading when the start transition runs. By resume it has usually
        // arrived — show it then, once per foreground visit.
        if (startedCount > 0 && !shownThisForeground && !AdsManager.fullscreenShowing &&
            isRealReturn()
        ) {
            if (AdsManager.tryShowAppOpen(activity)) {
                shownThisForeground = true
            }
        }
    }

    override fun onActivityStopped(activity: Activity) {
        startedCount = maxOf(0, startedCount - 1)
        if (startedCount == 0) {
            shownThisForeground = false
            lastBackgroundAt = System.currentTimeMillis()
        }
    }

    /**
     * Fresh process (never backgrounded) or backgrounded longer than the
     * floor. Anything shorter is a rotation or a flicker, not a return.
     */
    private fun isRealReturn(now: Long = System.currentTimeMillis()): Boolean =
        lastBackgroundAt == 0L || now - lastBackgroundAt >= MIN_BACKGROUND_MS

    companion object {
        internal const val MIN_BACKGROUND_MS = 10_000L
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) = Unit

    override fun onActivityResumed(activity: Activity) = Unit

    override fun onActivityPaused(activity: Activity) = Unit

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit

    override fun onActivityDestroyed(activity: Activity) = Unit
}
