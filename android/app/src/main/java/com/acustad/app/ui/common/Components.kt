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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.acustad.app.R
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
 * ### The three surfaces, and why this file is where they live
 *
 * There are exactly three levels and the whole app hangs off them (`PHASE_11_UI_UX.md` §1):
 *
 * | Level | Role | Where |
 * |---|---|---|
 * | 0 · canvas | the page | everywhere else |
 * | 1 · surface | anything you could **tap** | [BorderedPanel], [BorderedRow] |
 * | 2 · raised | quoted content from the database | [RaisedPanel] |
 *
 * **Rule 1: tappable ⇒ level 1 or above. Quoted from the database ⇒ level 2. Neither ⇒ canvas.**
 *
 * Both `BorderedPanel` and `BorderedRow` used to pass `Color.Transparent`, which meant every
 * card, every list row and the meaning block sat on raw canvas while `surface` and
 * `surface_alt` sat mapped in the theme and used nowhere. That is the entire reason the app
 * reads as a wireframe: not a missing design, a **built-and-unused** one. Two lines, and the
 * hierarchy exists.
 *
 * Every contrast pair these fills create was computed, not eyeballed (PHASE_11 §1), and all of
 * them are AA or better with one documented exception that [RaisedPanel] exists partly to make
 * avoidable: **`ink_muted` on `surface_alt` is 4.15:1, under the 4.5 text bar** — fine in dark
 * mode at 6.93:1, which is exactly how this kind of thing survives a dark-mode review and fails
 * on a cheap LCD in sunlight. So muted text never goes on a raised block. It is one rule, in
 * one KDoc, instead of a comment on forty call sites.
 *
 * The fill is **added to** the border, never a replacement for it. The 2dp ink border is the
 * sunlight guarantee; the fill is the hierarchy. A panel that reads as bare in sun gets a
 * stronger border or more text contrast — never a darker fill, and never a shadow.
 *
 * Taps are applied with `Modifier.clickable` on a plain `Surface` rather than with the
 * Material 3 `Surface(onClick = ...)` overload. That overload is still marked experimental in
 * places and takes a different parameter list, so relying on it costs a build for no benefit.
 */

@Composable
private fun inkBorder(): BorderStroke =
    BorderStroke(dimensionResource(R.dimen.border_width), MaterialTheme.colorScheme.outline)

/**
 * The one card radius, read from the token file.
 *
 * `design_tokens.xml` is documented as *"the ONLY place a colour, size or spacing is
 * defined"*, and until this change it was the only place those values were **written down** and
 * nowhere they were **used**: the app referenced `R.dimen` zero times and hardcoded 113 literal
 * `.dp` values. A token file that nothing reads is a comment. The three primitives read from it
 * first, and the rest of the app follows screen by screen alongside whatever else each screen
 * is being given — not in one sweeping diff, which would be a large change with no visible
 * benefit and a real chance of breaking a phone check.
 */
@Composable
private fun cardShape() = RoundedCornerShape(dimensionResource(R.dimen.radius_card))

/**
 * A bordered panel — the app's level-1 container: **cards, and anything you could tap.**
 *
 * Filled with `surface` so it reads as a surface rather than as an outline drawn on the page.
 * Not raised: a raised panel means *quoted from the database*, and that is [RaisedPanel]'s job
 * alone. Nesting one inside another is how a designed screen turns back into a generic one.
 */
@Composable
fun BorderedPanel(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = cardShape(),
        color = MaterialTheme.colorScheme.surface,
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

/**
 * Level 2: a block of content **quoted from the database** — a code's meaning, a source line,
 * a brand's own note. Exactly one per idea, and never nested inside another raised block.
 *
 * `surfaceVariant` is the theme's mapping of `surface_alt` (#DCEAF5 light, #1B2833 dark).
 *
 * **The rule that comes with this component: no muted text inside.** `ink_muted` on
 * `surface_alt` is **4.15:1**, which is under the 4.5:1 AA text bar — in light mode only, where
 * it is 6.93:1 and fine, which is precisely how this survives a dark-mode screenshot review
 * and then fails on a cheap LCD in sunlight. Use full `ink`, or move the label out onto the
 * canvas. Recomputed for this phase from the real resource values; the number is not a
 * round number someone liked.
 *
 * There is no `onClick` and no `elevation`, deliberately. A quotation is not a button, and a
 * raised card is how a considered layout becomes a template.
 */
@Composable
fun RaisedPanel(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = cardShape(),
        color = MaterialTheme.colorScheme.surfaceVariant,
        border = inkBorder(),
    ) {
        Column {
            content()
        }
    }
}

/** A bordered row container. Level 1, like [BorderedPanel]. 56dp is the minimum height, for gloves. */
@Composable
fun BorderedRow(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier.heightIn(min = dimensionResource(R.dimen.row_min)),
        shape = cardShape(),
        color = MaterialTheme.colorScheme.surface,
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
        shape = RoundedCornerShape(dimensionResource(R.dimen.radius_chip)),
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
