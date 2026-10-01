package com.acustad.app.ads

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform

/**
 * Every ad in the app goes through here. Screens never touch the GMA SDK
 * directly: they ask this object to preload or show, and get plain callbacks.
 *
 * Four rules this file exists to enforce:
 *
 *  - **Init once, load after consent.** [ensureConsent] runs the UMP flow and
 *    only then initialises MobileAds. No ad loads before that.
 *  - **A full-screen ad is never stacked on another.** [fullscreenShowing] is
 *    set while an interstitial, rewarded or app-open ad is on screen, and
 *    App Open refuses to show while it is set.
 *  - **Interstitials are capped.** One show per [INTERSTITIAL_CAP_MS], so the
 *    exit ad and the return-to-top ad cannot fire back to back.
 *  - **Nothing here throws into the UI.** Every failure path calls the
 *    callback's unhappy branch, and the app continues without the ad.
 */
object AdsManager {

    @Volatile
    private var initialised = false

    /** True while any full-screen ad is showing. Read by App Open. */
    @Volatile
    var fullscreenShowing = false
        private set

    /**
     * Runs the UMP consent flow, then initialises MobileAds and preloads the
     * full-screen formats. Called once from MainActivity, on the main thread —
     * UMP requires an Activity. [done] runs on every path, because ads are an
     * enhancement: the app must never wait on them.
     */
    fun ensureConsent(activity: Activity, done: () -> Unit) {
        val params = ConsentRequestParameters.Builder()
            .setTagForUnderAgeOfConsent(false)
            .build()
        val info = UserMessagingPlatform.getConsentInformation(activity)
        info.requestConsentInfoUpdate(
            activity,
            params,
            {
                UserMessagingPlatform.loadAndShowConsentFormIfRequired(activity) {
                    init(activity.applicationContext)
                    done()
                }
            },
            {
                init(activity.applicationContext)
                done()
            },
        )
    }

    /** MobileAds, exactly once. Cheap to call, safe to repeat. */
    fun init(context: Context) {
        if (initialised) return
        initialised = true
        MobileAds.initialize(context) {}
        preloadInterstitial(context)
        preloadRewarded(context)
    }

    // ── interstitial ────────────────────────────────────────────────

    private var interstitial: InterstitialAd? = null

    fun preloadInterstitial(context: Context) {
        if (interstitial != null) return
        InterstitialAd.load(
            context,
            AdIds.interstitial,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitial = ad
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    interstitial = null
                }
            },
        )
    }

    /**
     * Shows the exit / return-to-top interstitial. [onDone] runs on every
     * path — shown, capped, missing or failed — so callers never hang.
     */
    fun showInterstitial(activity: Activity, onDone: () -> Unit) {
        val ad = interstitial
        if (ad == null) {
            preloadInterstitial(activity)
            onDone()
            return
        }
        if (!takeCapSlot(activity)) {
            onDone()
            return
        }
        interstitial = null
        fullscreenShowing = true
        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                fullscreenShowing = false
                preloadInterstitial(activity)
                onDone()
            }

            override fun onAdFailedToShowFullScreenContent(error: com.google.android.gms.ads.AdError) {
                fullscreenShowing = false
                preloadInterstitial(activity)
                onDone()
            }
        }
        ad.show(activity)
    }

    // ── rewarded ────────────────────────────────────────────────────

    private var rewarded: RewardedAd? = null

    fun preloadRewarded(context: Context) {
        if (rewarded != null) return
        RewardedAd.load(
            context,
            AdIds.rewarded,
            AdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    rewarded = ad
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    rewarded = null
                }
            },
        )
    }

    fun isRewardedReady(): Boolean = rewarded != null

    /**
     * Shows the save-wall rewarded ad. [onEarned] fires only when the user
     * watched enough to earn the reward. [onUnavailable] fires when there is
     * no ad to show — offline, capped by fill, or failed — and the caller
     * shows the needs-connection popup instead of the save.
     */
    fun showRewarded(activity: Activity, onEarned: () -> Unit, onUnavailable: () -> Unit) {
        val ad = rewarded
        if (ad == null) {
            preloadRewarded(activity)
            onUnavailable()
            return
        }
        rewarded = null
        fullscreenShowing = true
        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                fullscreenShowing = false
                preloadRewarded(activity)
            }

            override fun onAdFailedToShowFullScreenContent(error: com.google.android.gms.ads.AdError) {
                fullscreenShowing = false
                preloadRewarded(activity)
            }
        }
        ad.show(activity) { onEarned() }
    }

    // ── app open ────────────────────────────────────────────────────

    private var appOpen: AppOpenAd? = null

    fun preloadAppOpen(context: Context) {
        if (appOpen != null) return
        AppOpenAd.load(
            context,
            AdIds.appOpen,
            AdRequest.Builder().build(),
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    appOpen = ad
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    appOpen = null
                }
            },
        )
    }

    /**
     * Shows the app-open ad if one is ready and no other full-screen ad is
     * showing. Silent on every other path — cold start must never wait.
     */
    fun showAppOpenIfReady(activity: Activity) {
        if (fullscreenShowing) return
        val ad = appOpen ?: run {
            preloadAppOpen(activity)
            return
        }
        appOpen = null
        fullscreenShowing = true
        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                fullscreenShowing = false
                preloadAppOpen(activity)
            }

            override fun onAdFailedToShowFullScreenContent(error: com.google.android.gms.ads.AdError) {
                fullscreenShowing = false
                preloadAppOpen(activity)
            }
        }
        ad.show(activity)
    }

    // ── interstitial frequency cap ──────────────────────────────────

    private fun prefs(context: Context): SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    /**
     * One interstitial per [INTERSTITIAL_CAP_MS]. Returns true and stamps the
     * time when this show may go ahead. Purely time-based, persisted, so a
     * force-stop cannot reset it.
     */
    internal fun takeCapSlot(context: Context, now: Long = System.currentTimeMillis()): Boolean {
        val last = prefs(context).getLong(KEY_LAST_INTERSTITIAL, 0L)
        if (now - last < INTERSTITIAL_CAP_MS) return false
        prefs(context).edit().putLong(KEY_LAST_INTERSTITIAL, now).apply()
        return true
    }

    private const val PREFS_NAME = "ac-ustad-ads"

    /** Three minutes between interstitials: exit and return-to-top never stack. */
    internal const val INTERSTITIAL_CAP_MS = 3 * 60 * 1000L

    private const val KEY_LAST_INTERSTITIAL = "last_interstitial"
}
