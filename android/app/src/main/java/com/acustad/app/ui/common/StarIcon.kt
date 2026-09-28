package com.acustad.app.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
