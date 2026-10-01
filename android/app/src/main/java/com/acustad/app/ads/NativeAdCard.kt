package com.acustad.app.ads

import android.content.Context
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
 * a request per row. Three shared ads, dealt round-robin, is the middle that
 * holds: at most three requests per screen, every slot filled.
 *
 * Ads that fail simply stay null and their slots are omitted — a failed ad is
 * empty space the list never shows, never a blank card.
 */
class NativeAdPool(context: Context, private val size: Int = 3) {

    private val appContext = context.applicationContext
    val ads = mutableStateListOf<NativeAd?>().apply { repeat(size) { add(null) } }

    fun load() {
        for (i in ads.indices) {
            if (ads[i] == null) loadOne(i)
        }
    }

    private fun loadOne(index: Int) {
        com.google.android.gms.ads.AdLoader.Builder(appContext, AdIds.native)
            .forNativeAd { ad -> ads[index] = ad }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    ads[index] = null
                }
            })
            .withNativeAdOptions(NativeAdOptions.Builder().build())
            .build()
            .loadAd(AdRequest.Builder().build())
    }

    fun adFor(slot: Int): NativeAd? = ads[slot % ads.size]

    fun destroy() {
        ads.forEach { it?.destroy() }
        ads.clear()
    }
}

@Composable
fun rememberNativeAdPool(size: Int = 3): NativeAdPool {
    val context = LocalContext.current
    val pool = remember { NativeAdPool(context, size) }
    DisposableEffect(pool) {
        pool.load()
        onDispose { pool.destroy() }
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
                factory = { context ->
                    (LayoutInflater.from(context).inflate(R.layout.native_ad, null) as NativeAdView)
                },
                update = { view ->
                    bindNativeAd(view, ad, ink, muted, primary, onPrimary)
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

private fun bindNativeAd(
    view: NativeAdView,
    ad: NativeAd,
    ink: Color,
    muted: Color,
    primary: Color,
    onPrimary: Color,
) {
    val headline = view.findViewById<TextView>(R.id.ad_headline)
    val body = view.findViewById<TextView>(R.id.ad_body)
    val media = view.findViewById<MediaView>(R.id.ad_media)
    val cta = view.findViewById<TextView>(R.id.ad_cta)
    val choices = view.findViewById<AdChoicesView>(R.id.ad_choices)

    headline.text = ad.headline
    headline.setTextColor(ink.toArgb())
    body.text = ad.body ?: ad.advertiser
    body.setTextColor(muted.toArgb())
    if (ad.mediaContent != null) {
        media.visibility = View.VISIBLE
        media.mediaContent = ad.mediaContent
    } else {
        media.visibility = View.GONE
    }
    cta.text = ad.callToAction
    cta.setBackgroundColor(primary.toArgb())
    cta.setTextColor(onPrimary.toArgb())

    view.headlineView = headline
    view.bodyView = body
    view.mediaView = media
    view.callToActionView = cta
    view.adChoicesView = choices
    view.setNativeAd(ad)
}
