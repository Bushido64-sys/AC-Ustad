package com.acustad.app.ui.common

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.acustad.app.ui.theme.UstadType

/**
 * The one border style in the app: 2dp ink, 4dp radius. The colour comes from
 * `colorScheme.outline`, which the theme maps to the `ink` resource, so the border and the
 * platform theme can never drift apart.
 */
@Composable
private fun inkBorder(): BorderStroke = BorderStroke(2.dp, MaterialTheme.colorScheme.outline)

/**
 * A bordered panel — the app's only container. Used for the home category panels and,
 * later, for grouped content on the detail screen.
 *
 * No soft shadow, no elevation, no gradient. A hard `3dp 3dp 0` shadow is allowed only on
 * primary actions and the selected state (RULES.md RULE 9), never on a panel.
 */
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
        onClick = onClick ?: {},
        enabled = onClick != null,
    ) { content() }
}

/** A 2dp-bordered row container. 56dp is the minimum height, for gloves. */
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
        onClick = onClick ?: {},
        enabled = onClick != null,
    ) { content() }
}

/**
 * A severity chip. The colour is never the only signal: the WORD is always present, and it
 * is what a screen reader announces first. 60% of all faults are `stop_pro`, so colour alone
 * would tell a technician nothing. (RULES.md RULE 8)
 *
 * @param label English in the light/EN state; the content-language switch supplies the word.
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
 * A count + noun on one line, e.g. "1 code" or "41 codes". Right-aligned in list rows,
 * always in IBM Plex Mono so a column of counts lines up.
 */
@Composable
fun CountLabel(count: Int, singular: String, plural: String, modifier: Modifier = Modifier) {
    Text(
        text = if (count == 1) "$count $singular" else "$count $plural",
        style = UstadType.count,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier.clearAndSetSemantics {},
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

/** A short column of stacked text, used inside panels. */
@Composable
fun PanelColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(modifier = modifier.padding(16.dp)) { content() }
}
