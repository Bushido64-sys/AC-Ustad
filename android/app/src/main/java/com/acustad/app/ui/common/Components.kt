package com.acustad.app.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.acustad.app.ui.theme.UstadType

/**
 * The building blocks of the app's look. Deliberately boring:
 *
 *  - a 2dp ink border on every container, drawn with `MaterialTheme.colorScheme.outline`,
 *    which the theme maps to the `ink` resource — so the border and the platform theme can
 *    never drift apart;
 *  - corner radius 0–4dp, never a pill;
 *  - no elevation and no shadow on containers. A hard `3dp 3dp 0` shadow is allowed only on
 *    primary actions and the selected state (RULES.md RULE 9).
 *
 * Taps are applied with `Modifier.clickable` on a plain `Surface` rather than with the
 * Material 3 `Surface(onClick = ...)` overload. That overload is still marked experimental in
 * places and takes a different parameter list, so relying on it costs a build for no benefit.
 */

@Composable
private fun inkBorder(): BorderStroke = BorderStroke(2.dp, MaterialTheme.colorScheme.outline)

/** A bordered panel — the app's only container type. */
@Composable
fun BorderedPanel(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        color = Color.Transparent,
        border = inkBorder(),
    ) {
        Column(
            modifier = if (onClick != null) {
                Modifier.clickable(onClick = onClick)
            } else {
                Modifier
            }
        ) {
            content()
        }
    }
}

/** A bordered row container. 56dp is the minimum height, for gloves. */
@Composable
fun BorderedRow(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier.heightIn(min = 56.dp),
        shape = RoundedCornerShape(4.dp),
        color = Color.Transparent,
        border = inkBorder(),
    ) {
        Row(
            modifier = if (onClick != null) {
                Modifier.clickable(onClick = onClick)
            } else {
                Modifier
            }
        ) {
            content()
        }
    }
}

/**
 * A severity chip. The colour is never the only signal: the word is always present, and it is
 * what a screen reader announces first. 60% of all faults are `stop_pro`, so colour alone
 * would tell a technician nothing. (RULES.md RULE 8)
 */
@Composable
fun SeverityChip(
    label: String,
    background: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    border: BorderStroke? = null,
) {
    Surface(
        modifier = modifier.semantics { contentDescription = label },
        shape = RoundedCornerShape(2.dp),
        color = background,
        border = border,
    ) {
        Text(
            text = label,
            style = UstadType.label,
            color = contentColor,
            maxLines = 1,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        )
    }
}

/**
 * A count with its noun, e.g. "1 code" or "41 codes". Right-aligned in list rows, always in
 * IBM Plex Mono so a column of counts lines up.
 */
@Composable
fun CountLabel(count: Int, singular: String, plural: String, modifier: Modifier = Modifier) {
    val text = if (count == 1) "$count $singular" else "$count $plural"
    Text(
        text = text,
        style = UstadType.count,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier.clearAndSetSemantics { },
    )
}

/** A section heading in IBM Plex Sans Condensed, with an optional right-aligned count. */
@Composable
fun SectionHeading(
    title: String,
    modifier: Modifier = Modifier,
    trailing: String? = null,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = title, style = UstadType.section, maxLines = 1)
        if (trailing != null) {
            Text(text = trailing, style = UstadType.count, maxLines = 1)
        }
    }
}

/** A padded column, used inside panels. */
@Composable
fun PanelColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(modifier = modifier.padding(16.dp)) {
        content()
    }
}

/**
 * The hard `3dp 3dp 0` shadow: an opaque rectangle offset by a fixed amount, drawn behind.
 *
 * Not `Modifier.shadow`. That is a real drop shadow — it blurs, softens and shifts colour with
 * whatever is underneath, which is the generic elevated-card look this design is trying not to
 * look like. The neo-brutalist shadow is a flat ink copy of the shape displaced by a constant,
 * so it is drawn directly and is identical in light and dark mode, because it is a fill rather
 * than a computed highlight.
 *
 * Rectangular on purpose, and that is the whole design: the only caller is the selected
 * bottom-nav indicator, which is a 4dp bar. (RULES.md RULE 9)
 *
 * Allowed on **primary actions and the selected state only**. A shadow on every card is what
 * turns a considered design into a template, so there is exactly one caller in the app.
 *
 * Drawn behind the content, because `drawBehind` runs before content is drawn.
 */
@Composable
fun Modifier.hardShadow(
    offset: Dp = 3.dp,
    color: Color = MaterialTheme.colorScheme.outline,
): Modifier = this.drawBehind {
    val shift = offset.toPx()
    drawRect(color = color, topLeft = Offset(shift, shift))
}
