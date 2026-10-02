package com.acustad.app.ads

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.acustad.app.R
import com.acustad.app.ui.common.BorderedRow
import com.acustad.app.ui.theme.UstadType
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.AdChoicesView
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView

/**
 * A pool of native ads for one screen. The code list shows a slot every five
 * rows, so one ad is not enough — but each slot loading its own ad would fire
 * a request per row. Three ads is the middle that holds: at most three
 * requests per screen, and call sites render at most three slots so each ad
 * backs exactly one view (sharing one NativeAd between two cards blanks the
 * first — the SDK allows one ad per view only).
 *
 * Ads that fail simply stay null and their slots are omitted in release — a
 * failed ad is empty space the list never shows, never a blank card.
 */
class NativeAdPool(context: Context, private val size: Int = 3) {

    // AdLoader needs an Activity context, not the application context: the
    // native template inflates MediaView + AdChoices overlay into the
    // activity window, and loads issued from the application context
    // silently never fill on-device (empty slots on all four placements).
    // Keep the Activity reference only for the Builder call below, never
    // beyond the pool lifetime (destroyed on dispose).
    private val adContext: Context = context
    val ads = mutableStateListOf<NativeAd?>().apply { repeat(size) { add(null) } }

    /**
     * Set on dispose. Late SDK callbacks check it before touching the list:
     * without it, an ad arriving after the screen left would index into a
     * cleared list — an IndexOutOfBounds crash from third-party timing, found
     * in the 2026-10-01 crash audit.
     */
    @Volatile
    private var destroyed = false

    /**
     * Retries per slot, so a failure is not final. The first load fires while
     * MobileAds is still initialising, when failures are routine — each empty
     * slot gets up to [MAX_RETRIES] more attempts, 15s apart. Bounded, not a
     * timer that loads forever: a slot that cannot fill stops asking.
     */
    private val retries = mutableMapOf<Int, Int>()
    private val handler = Handler(Looper.getMainLooper())

    /**
     * Last load failure per slot, for the on-screen diagnostic line.
     * Without adb on the test phone, logcat is unreachable — the slot
     * itself reports loading / failed(code) / loaded, DEBUG builds only.
     * TEMPORARY: remove with [debugState] and [NativeSlotDebug] once the
     * no-fill cause is confirmed on a phone.
     */
    private val lastError = mutableMapOf<Int, String>()

    fun load() {
        if (destroyed) return
        runCatching {
            for (i in ads.indices) {
                if (ads[i] == null) loadOne(i)
            }
        }
    }

    private fun loadOne(index: Int) {
        runCatching {
            com.google.android.gms.ads.AdLoader.Builder(adContext, AdIds.native)
                .forNativeAd { ad ->
                    if (!destroyed && index < ads.size) {
                        ads[index] = ad
                        lastError.remove(index)
                        Log.d(TAG, "native loaded slot=$index")
                    } else {
                        ad.destroy()
                    }
                }
                .withAdListener(object : AdListener() {
                    override fun onAdFailedToLoad(error: LoadAdError) {
                        val msg =
                            "code=${error.code} ${error.domain} ${error.message}"
                        lastError[index] = msg
                        Log.w(TAG, "native failed slot=$index $msg")
                        if (destroyed || index >= ads.size) return
                        ads[index] = null
                        val attempts = (retries[index] ?: 0) + 1
                        retries[index] = attempts
                        if (attempts <= MAX_RETRIES) {
                            handler.postDelayed(
                                {
                                    if (!destroyed && index < ads.size && ads[index] == null) {
                                        loadOne(index)
                                    }
                                },
                                RETRY_MS,
                            )
                        }
                    }
                })
                .withNativeAdOptions(NativeAdOptions.Builder().build())
                .build()
                .loadAd(AdRequest.Builder().build())
        }.onFailure {
            Log.w(TAG, "native load threw slot=$index", it)
        }
    }

    fun adFor(slot: Int): NativeAd? =
        if (destroyed || ads.isEmpty()) null else ads[slot % ads.size]

    /**
     * One-line slot state for the on-screen diagnostic. TEMPORARY, see
     * [lastError]. DEBUG builds only — release never renders it.
     */
    fun debugState(index: Int): String =
        when {
            destroyed || index >= ads.size -> "slot $index: destroyed"
            ads[index] != null ->
                if (ads[index]?.headline == null) "slot $index: loaded, NO HEADLINE" else "slot $index: loaded"
            else -> "slot $index: ${lastError[index] ?: "loading…"}"
        }

    fun destroy() {
        destroyed = true
        handler.removeCallbacksAndMessages(null)
        ads.forEach { runCatching { it?.destroy() } }
        ads.clear()
    }

    companion object {
        private const val TAG = "NativeAdPool"
        private const val MAX_RETRIES = 3
        private const val RETRY_MS = 15_000L
    }
}

@Composable
fun rememberNativeAdPool(size: Int = 3): NativeAdPool {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val pool = remember(context, size) { NativeAdPool(context, size) }
    DisposableEffect(pool, lifecycle) {
        pool.load()
        // Second chance for empty slots: the first load fires while MobileAds
        // is still initialising, so early failures are routine rather than
        // final. Resume retries only the slots that stayed empty.
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                runCatching { pool.load() }
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
            pool.destroy()
        }
    }
    return pool
}

/**
 * One native ad in a bordered card that rhymes with the list rows but never
 * passes as one.
 *
 * The compliance contract, enforced by structure rather than memory:
 *
 *  - the **"Ad" badge sits on top**, outside the SDK view, in the app's own
 *    label style — it cannot be covered, cropped or forgotten by an ad
 *    creative, because it is not part of the creative;
 *  - the **AdChoices icon** is registered on the SDK view, so the overlay the
 *    SDK draws always has a place to live;
 *  - the **call to action is a verb button** in the primary fill — the only
 *    thing on the card that looks tappable, because it is the only thing
 *    that is;
 *  - the card itself is **not clickable** and never navigates: a tap anywhere
 *    but the ad's own assets does nothing, and only the assets the SDK owns
 *    (headline, media, CTA) are registered with it.
 *
 * Text colours are applied from the theme at bind time, because an XML
 * `textColor` is a static resource and would freeze the light palette into
 * dark mode (trap 22's whole lesson). Muted body text sits on the card
 * surface, never on a raised one (trap 24).
 */
@Composable
fun NativeAdCard(ad: NativeAd, modifier: Modifier = Modifier) {
    // Headline is the one asset Google guarantees. Without it this is not a
    // renderable ad — omit the card rather than showing a badge with nothing.
    val headline = ad.headline ?: return
    val context = LocalContext.current
    // Inflated once per ad. If inflation itself fails, the whole card is
    // omitted: a badge with no ad is noise, and a crash here would take down
    // the list the ad sits in.
    val nativeView = remember(ad) {
        runCatching {
            LayoutInflater.from(context).inflate(R.layout.native_ad, null) as NativeAdView
        }.getOrNull()
    } ?: return
    val ink = MaterialTheme.colorScheme.onSurface
    val muted = MaterialTheme.colorScheme.onSurfaceVariant
    val primary = MaterialTheme.colorScheme.primary
    val onPrimary = MaterialTheme.colorScheme.onPrimary

    BorderedRow(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.padding(start = 12.dp, top = 12.dp, end = 12.dp)) {
                AdBadge()
            }
            AndroidView(
                factory = { nativeView },
                update = { view ->
                    runCatching { bindNativeAd(view, ad, headline, ink, muted, primary, onPrimary) }
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun AdBadge() {
    Text(
        text = stringResource(R.string.ad_badge),
        style = UstadType.label,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

/**
 * TEMPORARY diagnostic line rendered in place of an unfilled native slot.
 * DEBUG builds only — lets a phone without adb report load vs render
 * failure on the screen itself. Remove with [NativeAdPool.debugState]
 * once the no-fill cause is confirmed.
 */
@Composable
fun NativeSlotDebug(text: String, modifier: Modifier = Modifier) {
    Text(
        text = "NATIVE DEBUG: $text",
        style = UstadType.label,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
    )
}

private fun bindNativeAd(
    view: NativeAdView,
    ad: NativeAd,
    headline: String,
    ink: Color,
    muted: Color,
    primary: Color,
    onPrimary: Color,
) {
    val headlineView = view.findViewById<TextView>(R.id.ad_headline)
    val body = view.findViewById<TextView>(R.id.ad_body)
    val media = view.findViewById<MediaView>(R.id.ad_media)
    val cta = view.findViewById<TextView>(R.id.ad_cta)
    val choices = view.findViewById<AdChoicesView>(R.id.ad_choices)

    headlineView.text = headline
    headlineView.setTextColor(ink.toArgb())
    val bodyText = ad.body ?: ad.advertiser
    if (bodyText != null) {
        body.visibility = View.VISIBLE
        body.text = bodyText
        body.setTextColor(muted.toArgb())
        view.bodyView = body
    } else {
        body.visibility = View.GONE
    }
    if (ad.mediaContent != null) {
        media.visibility = View.VISIBLE
        media.mediaContent = ad.mediaContent
        view.mediaView = media
    } else {
        media.visibility = View.GONE
    }
    val ctaText = ad.callToAction
    if (ctaText != null) {
        cta.visibility = View.VISIBLE
        cta.text = ctaText
        cta.setBackgroundColor(primary.toArgb())
        cta.setTextColor(onPrimary.toArgb())
        view.callToActionView = cta
    } else {
        cta.visibility = View.GONE
    }

    view.headlineView = headlineView
    view.adChoicesView = choices
    view.setNativeAd(ad)
}
