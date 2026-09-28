package com.acustad.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.acustad.app.R

/**
 * The app's colours live in ONE place: `res/values/colors.xml` (light) and
 * `res/values-night/colors.xml` (dark), which are copies of the guide's
 * `design_tokens.xml`. This file deliberately does NOT hard-code a single hex value.
 *
 * That matters. An earlier version defined the palette in Kotlin *and* in XML, which is two
 * sources of truth for the same 20 colours — exactly the drift the build guide warns about in
 * RULE 10. Now the platform theme and the Compose layer read the same resource, so they cannot
 * disagree.
 *
 * Measured pairings (all AA or better unless noted):
 *   ink on canvas            16.89:1 light · 17.46:1 dark
 *   ink_muted on canvas       5.09:1 light ·  8.76:1 dark
 *   white on primary          5.87:1
 *   white on error            6.37:1
 *   white on blue_300         2.27:1  FAILS — never put white text on blue_300
 */

@Composable
private fun lightScheme() = lightColorScheme(
    primary = colorResource(R.color.blue_600),
    onPrimary = colorResource(R.color.on_accent),
    primaryContainer = colorResource(R.color.surface),
    onPrimaryContainer = colorResource(R.color.ink),
    secondary = colorResource(R.color.blue_500),
    onSecondary = colorResource(R.color.on_accent),
    background = colorResource(R.color.canvas),
    onBackground = colorResource(R.color.ink),
    surface = colorResource(R.color.surface),
    onSurface = colorResource(R.color.ink),
    surfaceVariant = colorResource(R.color.surface_alt),
    onSurfaceVariant = colorResource(R.color.ink_muted),
    // outline IS the 2dp ink border the whole design is built on; hairline is a divider
    outline = colorResource(R.color.ink),
    outlineVariant = colorResource(R.color.hairline),
    error = colorResource(R.color.signal_deep),
    onError = colorResource(R.color.on_accent),
    errorContainer = colorResource(R.color.signal_tint),
    onErrorContainer = colorResource(R.color.ink),
)

@Composable
private fun darkScheme() = darkColorScheme(
    primary = colorResource(R.color.blue_600),
    onPrimary = colorResource(R.color.on_accent),
    primaryContainer = colorResource(R.color.surface_alt),
    onPrimaryContainer = colorResource(R.color.ink),
    secondary = colorResource(R.color.blue_300),
    onSecondary = colorResource(R.color.canvas),
    background = colorResource(R.color.canvas),
    onBackground = colorResource(R.color.ink),
    surface = colorResource(R.color.surface),
    onSurface = colorResource(R.color.ink),
    surfaceVariant = colorResource(R.color.surface_alt),
    onSurfaceVariant = colorResource(R.color.ink_muted),
    outline = colorResource(R.color.ink),
    outlineVariant = colorResource(R.color.hairline),
    error = colorResource(R.color.signal_deep),
    onError = colorResource(R.color.on_accent),
    errorContainer = colorResource(R.color.signal_tint),
    onErrorContainer = colorResource(R.color.signal_tint_3),
)

/**
 * Sharp corners, always. Corner radius never exceeds 4dp and nothing is ever a pill — that
 * single rule does more for the "instrument, not template" feel than any colour decision.
 * (RULES.md RULE 9)
 */
val UstadShapes = Shapes(
    extraSmall = RoundedCornerShape(0.dp),
    small = RoundedCornerShape(2.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(4.dp),
    extraLarge = RoundedCornerShape(4.dp),
)

/**
 * @param dark null follows the system, true forces dark, false forces light (Settings).
 */
@Composable
fun AcUstadTheme(
    dark: Boolean? = null,
    content: @Composable () -> Unit,
) {
    val useDark = if (dark == null) isSystemInDarkTheme() else dark
    // Dynamic colour is deliberately NOT used: it would overwrite this palette entirely on
    // Android 12+, and the palette is the product. (PHASE_1_SETUP.md §10)
    val colors = if (useDark) darkScheme() else lightScheme()
    MaterialTheme(
        colorScheme = colors,
        typography = UstadTypography,
        shapes = UstadShapes,
        content = content,
    )
}
