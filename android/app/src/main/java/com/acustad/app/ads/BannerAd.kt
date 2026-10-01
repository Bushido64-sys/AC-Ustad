package com.acustad.app.ads

import android.app.Activity
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

/**
 * One adaptive banner, pinned to the bottom of the screen by its caller.
 *
 * The slot reserves the ad's own height before anything loads, so a slow or
 * failed ad never pushes content around — the failure mode PHASE_10 warned
 * about, where a late banner steals the tap meant for a fix step. When no ad
 * fills, the slot is simply empty space of the same size.
 *
 * Anchored adaptive, not fixed: it spans the screen width on every phone,
 * which is what earns in a low-eCPM market — a 320-wide banner on a 410-wide
 * phone leaves money and layout on the table. Refresh is server-side (30s);
 * mediated UIs must keep their own refresh off so the slot never double-
 * refreshes.
 */
@Composable
fun BannerAd(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val activity = context as? Activity ?: return
    val widthDp = LocalConfiguration.current.screenWidthDp
    val density = LocalDensity.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    // The size for this exact width, computed once per width. Height is known
    // up front, so the slot below reserves it before the ad exists.
    val adSize = remember(widthDp) {
        AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, widthDp)
    }
    val slotHeight = remember(adSize, density) {
        with(density) { adSize.getHeightInPixels(context).toDp() }
    }

    val adView = remember(adSize) {
        runCatching {
            AdView(context).apply {
                setAdSize(adSize)
                adUnitId = AdIds.banner
                adListener = object : AdListener() {}
                loadAd(AdRequest.Builder().build())
            }
        }.getOrNull()
    }

    // The SDK failed before first paint: keep the reserved slot, show nothing.
    // A banner that cannot exist must never take the screen down with it.
    if (adView == null) {
        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(slotHeight),
        )
        return
    }

    DisposableEffect(lifecycle, adView) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> runCatching { adView.resume() }
                Lifecycle.Event.ON_PAUSE -> runCatching { adView.pause() }
                Lifecycle.Event.ON_DESTROY -> runCatching { adView.destroy() }
                else -> Unit
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
            runCatching { adView.destroy() }
        }
    }

    AndroidView(
        factory = { adView },
        modifier = modifier
            .fillMaxWidth()
            .height(slotHeight),
    )
}
