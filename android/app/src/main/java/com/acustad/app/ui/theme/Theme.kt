package com.acustad.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.acustad.app.R

/**
 * The app's colours live in ONE place: `res/values/colors.xml` for the light palette and
 * `res/values/colors_dark.xml` for the dark one, both copies of the guide's
 * `design_tokens.xml`. This file deliberately does NOT hard-code a single hex value.
 *
 * That matters. An earlier version defined the palette in Kotlin *and* in XML, which is two
 * sources of truth for the same 20 colours - exactly the drift the build guide warns about in
 * RULE 10. Now the platform theme and the Compose layer read the same resource, so they cannot
 * disagree.
 *
 * ### Both palettes are ordinary resources, and that is deliberate
 *
 * The dark palette used to live in `res/values-night/`, which is the obvious place for it and is
 * **wrong the moment a user can override the theme**. Android picks `values/` versus
 * `values-night/` using the phone's night setting and nothing else - it cannot know that
 * Settings says Light. So on a dark phone with Light selected, `colorResource(R.color.canvas)`
 * returned the dark canvas to a light palette, and the app looked like the override did nothing
 * at all. (PROGRESS trap 22)
 *
 * The two palette files therefore sit side by side in the default folder under names that say
 * which is which, and [ustadColor] picks between them. `values-night/` now exists for one thing
 * only: the platform window background, which is painted before any Compose code exists and so
 * genuinely has to follow the system.
 *
 * Measured pairings (all AA or better unless noted):
 *   ink on canvas            16.89:1 light · 17.46:1 dark
 *   ink_muted on canvas       5.09:1 light ·  8.76:1 dark
 *   white on primary          5.87:1
 *   white on error            6.37:1
 *   white on blue_300         2.27:1  FAILS - never put white text on blue_300
 */

/**
 * Which palette the app has actually chosen, provided by [AcUstadTheme].
 *
 * `false` by default, which is the light palette - the same default as a fresh install, and a
 * deliberate choice over `error(...)`. A missing provider should render the app's documented
 * default rather than crash, because the alternative is a preview or a test crashing on a
 * technicality while the real failure being guarded against is a *silently wrong* colour.
 */
val LocalUstadDark = staticCompositionLocalOf { false }

/**
 * Reads a colour from whichever palette the app has chosen, for code that is **too deep in the
 * tree to be told** which one it is. Its only caller is `Severity.kt`, whose chip colours are
 * built inside screens and lists.
 *
 * The two scheme builders below do **not** use this — they name their tokens outright, because
 * they already know which palette they are and a flag would only be a way to get it wrong.
 *
 * @param light the resource to use in the light palette.
 * @param dark the resource to use in the dark palette. **Defaults to [light]**, so a token that
 *   is genuinely the same in both modes is written once, with one name and one value, and there
 *   is no second copy of `#1668A8` to fall out of step. Passing a different `dark` is what makes
 *   a token mode-aware, and it is done only for the ones that really differ.
 * @param isDark which palette to read. Defaults to the app's current choice, so a caller cannot
 *   accidentally disagree with what is on screen.
 */
@Composable
fun ustadColor(
    light: Int,
    dark: Int = light,
    isDark: Boolean = LocalUstadDark.current,
): Color = colorResource(if (isDark) dark else light)

/**
 * The LIGHT scheme. Every name here is a light token, read directly.
 *
 * Deliberately **not** going through [ustadColor]: this function is called only when the app has
 * already decided to be light, so there is nothing left to decide and passing a flag through
 * would only create a way to hand this function the dark palette. Naming the light tokens
 * outright makes that mistake unrepresentable rather than merely unlikely.
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

/**
 * The DARK scheme, naming the dark token of every slot. See [lightScheme] for why these are
 * written out rather than selected.
 *
 * Note that `signal_deep` becomes `signal_on_dark` and `signal_tint` becomes
 * `signal_dark_surface`: the alert hue is **lifted** for a dark surface rather than dimmed, and
 * reusing the light token here would have produced an orange chip at 2:1 on a near-black canvas.
 */
@Composable
private fun darkScheme() = darkColorScheme(
    primary = colorResource(R.color.blue_600),
    onPrimary = colorResource(R.color.on_accent),
    primaryContainer = colorResource(R.color.surface_alt_dark),
    onPrimaryContainer = colorResource(R.color.ink_invert),
    secondary = colorResource(R.color.blue_300),
    onSecondary = colorResource(R.color.canvas_dark),
    background = colorResource(R.color.canvas_dark),
    onBackground = colorResource(R.color.ink_invert),
    surface = colorResource(R.color.surface_dark),
    onSurface = colorResource(R.color.ink_invert),
    surfaceVariant = colorResource(R.color.surface_alt_dark),
    onSurfaceVariant = colorResource(R.color.ink_muted_dark),
    outline = colorResource(R.color.ink_invert),
    outlineVariant = colorResource(R.color.hairline_dark),
    error = colorResource(R.color.signal_on_dark),
    onError = colorResource(R.color.on_accent),
    errorContainer = colorResource(R.color.signal_dark_surface),
    // `signal_dark_text`, not another `signal_dark_surface`. The previous dark scheme resolved
    // onErrorContainer to `signal_tint_3`, which the night qualifier made the SAME value as
    // errorContainer - so this pair was text on its own background, i.e. invisible. Nothing
    // reads onErrorContainer in this app, so it was never visible; `signal_dark_text` is what
    // design_tokens.xml defines for exactly this pairing, at 9.19:1.
    onErrorContainer = colorResource(R.color.signal_dark_text),
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
 *
 * This resolves the **only** ambiguity there is - a Boolean - and then hands the answer down as
 * [LocalUstadDark]. Everything below it reads palette values through [ustadColor], so a theme
 * change repaints the entire tree from one place and there is no screen that can hold a colour
 * from the other palette.
 *
 * The override is real now. It was not, while the dark palette lived in `values-night/`: the
 * Boolean changed which *shape* of scheme was built and the *values* still came from the
 * phone. See the KDoc at the top of this file and PROGRESS trap 22.
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
    ) {
        // Provided inside MaterialTheme, so a composable can read either. The scheme builders
        // above deliberately pass `isDark` explicitly and do NOT rely on this.
        CompositionLocalProvider(LocalUstadDark provides useDark, content = content)
    }
}
