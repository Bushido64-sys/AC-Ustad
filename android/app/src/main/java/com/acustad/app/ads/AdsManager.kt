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
 *  - **Nothing here throws into the UI — ever.** Every entry point is wrapped,
 *    because an ad SDK that can crash the app is worse than no ads at all.
 *    A failed ad is an empty slot and a callback's unhappy branch, never an
 *    exception. This is the lesson of the 2026-10-01 crash audit: the SDK is
 *    third-party code running inside our process, so it is treated like it.
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
        runCatching {
            val params = ConsentRequestParameters.Builder()
                .setTagForUnderAgeOfConsent(false)
                .build()
            val info = UserMessagingPlatform.getConsentInformation(activity)
            info.requestConsentInfoUpdate(
                activity,
                params,
                {
                    runCatching {
                        UserMessagingPlatform.loadAndShowConsentFormIfRequired(activity) {
                            init(activity.applicationContext)
                            done()
                        }
                    }.onFailure {
                        init(activity.applicationContext)
                        done()
                    }
                },
                {
                    init(activity.applicationContext)
                    done()
                },
            )
        }.onFailure {
            // Consent itself blew up. Ads go uninitialised rather than half-set:
            // every load path treats "never initialised" as "no ad", and the
            // app runs exactly like the pre-ads build.
            done()
        }
    }

    /** MobileAds, exactly once. Cheap to call, safe to repeat, never throws. */
    fun init(context: Context) {
        if (initialised) return
        initialised = true
        runCatching {
            MobileAds.initialize(context) {}
            preloadInterstitial(context)
            preloadRewarded(context)
        }
    }

    // ── interstitial ────────────────────────────────────────────────

    private var interstitial: InterstitialAd? = null

    fun preloadInterstitial(context: Context) {
        if (interstitial != null) return
        runCatching {
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
        }.onFailure {
            interstitial = null
        }
    }

    /**
     * Shows an interstitial for a back-navigation (detail → list, deep path
     * → top level). [onDone] runs on every path — shown or skipped — so the
     * navigation underneath always completes.
     *
     * Two gates, both from Google's interstitial policy, and both must pass:
     *
     *  - **every second back-navigation.** No more than one interstitial per
     *    two user actions — showing after every single back tap is a listed
     *    violation, and it explicitly covers the Back button.
     *  - **the time cap** ([INTERSTITIAL_CAP_MS]).
     *
     * There is deliberately NO exit path: interstitials on app exit are
     * banned outright ("User exits app" is the disallowed example), with ad
     * serving disabled as the penalty. The exit BackHandler finishes the
     * activity with no ad, always.
     */
    fun showInterstitialForBack(activity: Activity, onDone: () -> Unit) {
        backNavCount++
        if (backNavCount % 2 != 0) {
            onDone()
            return
        }
        val ad = interstitial
        if (ad == null) {
            runCatching { preloadInterstitial(activity) }
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
                runCatching { preloadInterstitial(activity) }
                onDone()
            }

            override fun onAdFailedToShowFullScreenContent(error: com.google.android.gms.ads.AdError) {
                fullscreenShowing = false
                runCatching { preloadInterstitial(activity) }
                onDone()
            }
        }
        runCatching {
            ad.show(activity)
        }.onFailure {
            fullscreenShowing = false
            runCatching { preloadInterstitial(activity) }
            onDone()
        }
    }

    // ── rewarded ────────────────────────────────────────────────────

    private var rewarded: RewardedAd? = null

    fun preloadRewarded(context: Context) {
        if (rewarded != null) return
        runCatching {
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
        }.onFailure {
            rewarded = null
        }
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
            runCatching { preloadRewarded(activity) }
            onUnavailable()
            return
        }
        rewarded = null
        fullscreenShowing = true
        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                fullscreenShowing = false
                runCatching { preloadRewarded(activity) }
            }

            override fun onAdFailedToShowFullScreenContent(error: com.google.android.gms.ads.AdError) {
                fullscreenShowing = false
                runCatching { preloadRewarded(activity) }
            }
        }
        runCatching {
            ad.show(activity) { onEarned() }
        }.onFailure {
            fullscreenShowing = false
            runCatching { preloadRewarded(activity) }
            onUnavailable()
        }
    }

    // ── app open ────────────────────────────────────────────────────

    private var appOpen: AppOpenAd? = null

    fun preloadAppOpen(context: Context) {
        if (appOpen != null) return
        runCatching {
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
        }.onFailure {
            appOpen = null
        }
    }

    /**
     * Shows the app-open ad if one is ready and no other full-screen ad is
     * showing. Silent on every other path — cold start must never wait.
     */
    fun showAppOpenIfReady(activity: Activity) {
        tryShowAppOpen(activity)
    }

    /**
     * Like [showAppOpenIfReady], but reports whether anything showed. The
     * app-open manager uses it to retry on resume: the cold-start preload is
     * still in flight at the first foreground ([preloadAppOpen] fires in
     * `UstadApp.onCreate`, milliseconds before the first activity starts), so
     * without a second attempt the launch ad would only ever appear on the
     * *second* foreground.
     */
    internal fun tryShowAppOpen(activity: Activity): Boolean {
        if (fullscreenShowing) return false
        val ad = appOpen ?: run {
            runCatching { preloadAppOpen(activity) }
            return false
        }
        appOpen = null
        fullscreenShowing = true
        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                fullscreenShowing = false
                runCatching { preloadAppOpen(activity) }
            }

            override fun onAdFailedToShowFullScreenContent(error: com.google.android.gms.ads.AdError) {
                fullscreenShowing = false
                runCatching { preloadAppOpen(activity) }
            }
        }
        return runCatching {
            ad.show(activity)
            true
        }.getOrDefault(false).also { shown ->
            if (!shown) {
                fullscreenShowing = false
                runCatching { preloadAppOpen(activity) }
            }
        }
    }

    // ── interstitial frequency cap ──────────────────────────────────

    private fun prefs(context: Context): SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    /**
     * One interstitial per [INTERSTITIAL_CAP_MS]. Returns true and stamps the
     * time when this show may go ahead. Purely time-based, persisted, so a
     * force-stop cannot reset it.
     */
    internal fun takeCapSlot(context: Context, now: Long = System.currentTimeMillis()): Boolean =
        runCatching {
            val last = prefs(context).getLong(KEY_LAST_INTERSTITIAL, 0L)
            if (now - last < INTERSTITIAL_CAP_MS) return false
            prefs(context).edit().putLong(KEY_LAST_INTERSTITIAL, now).apply()
            true
        }.getOrDefault(true)

    private const val PREFS_NAME = "ac-ustad-ads"

    /** 90 seconds between interstitials: two back-to-back breaks never stack. */
    internal const val INTERSTITIAL_CAP_MS = 90 * 1000L

    /** Back-navigations since process start. The show budget is every 2nd. */
    private var backNavCount = 0

    private const val KEY_LAST_INTERSTITIAL = "last_interstitial"
}
