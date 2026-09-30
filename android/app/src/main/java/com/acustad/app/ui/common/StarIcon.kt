package com.acustad.app.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Canvas
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.toggleableState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.acustad.app.R
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * The save star, drawn rather than imported.
 *
 * `Icons.Filled.Star` is in material-icons-core, but `StarBorder` is only in
 * material-icons-extended - roughly 10 MB of icons for one glyph, against an APK budget of
 * 12 MB and a five-icon rule (RULES.md RULE 11, ASSETS.md §5). So the two states are drawn
 * from one path: filled when saved, a 2dp stroke when not.
 *
 * The filled/outline difference is deliberate and not decorative: a saved code must be
 * identifiable at a glance, and tint alone fails on a washed-out screen in sunlight.
 *
 * It is a 5-point star with a flat, geometric construction to match the sharp-cornered
 * design rather than a rounded one.
 */
@Composable
fun StarIcon(
    filled: Boolean,
    tint: Color,
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    strokeWidth: Dp = 2.dp,
) {
    Canvas(modifier = modifier.size(size)) {
        val outer = this.size.minDimension / 2f
        val inner = outer * 0.42f
        val centre = Offset(this.size.width / 2f, this.size.height / 2f)
        val path = Path()
        // Start at the top and step around in 36-degree increments, alternating radii.
        for (i in 0 until 10) {
            val radius = if (i % 2 == 0) outer else inner
            val angle = -PI / 2.0 + i * PI / 5.0
            val x = centre.x + (radius * cos(angle)).toFloat()
            val y = centre.y + (radius * sin(angle)).toFloat()
            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        path.close()
        if (filled) {
            drawPath(path, color = tint)
        } else {
            drawPath(path, color = tint, style = Stroke(width = strokeWidth.toPx()))
        }
    }
}

/**
 * The save star as a **control**: a 48dp tap target, a filled/outline state, and a spoken label
 * that changes with the state.
 *
 * Extracted from the code-detail screen's body and shared with the app bar, because the star now
 * lives in the app bar (DESIGN.md §4.5, PHASE_8 §3) and one implementation of "what does saved
 * look like" is the only way the two can never disagree. There are two call sites and they mean
 * the same thing; a second drawing of the star is a second thing to get wrong.
 *
 * Starred is the **primary blue**, because in this app blue means "selected". Unstarred is ink.
 * The label is the important half: a screen reader announces "Save this code" or "Remove from
 * saved", never the word "star", and it is the state a listener needs rather than the shape.
 */
@Composable
fun StarToggle(
    filled: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val label = stringResource(if (filled) R.string.action_unstar else R.string.action_star)
    IconButton(
        onClick = onToggle,
        modifier = modifier
            .size(TOUCH_TARGET_DP)
            .semantics {
                contentDescription = label
                // Announced as a toggle, so a listener says "on"/"off" without the app having to
                // encode the state into the label twice.
                toggleableState = filled
            },
    ) {
        StarIcon(
            filled = filled,
            tint = if (filled) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurface
            },
        )
    }
}

/** 48dp: the app's touch-target floor, for gloves (RULE 16). */
private val TOUCH_TARGET_DP = 48.dp
