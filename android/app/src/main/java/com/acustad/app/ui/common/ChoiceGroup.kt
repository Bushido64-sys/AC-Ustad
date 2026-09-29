package com.acustad.app.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.acustad.app.ui.theme.UstadType

/**
 * A row of mutually exclusive options, with a title above and an optional caption below.
 *
 * **There are two of these in the app** — the content language and the theme — and this is the
 * one implementation of both. They are the same control: same 48dp targets, same 2dp ink border
 * on every state, same "selected is the structural blue with white text" fill. Written twice,
 * the second copy would drift, and a settings screen whose two rows disagree by two pixels is
 * the most visible possible evidence of that.
 *
 * The generic parameter is the option type rather than a label/selected pair list, so the caller
 * cannot get the index of a label and the index of its selected flag out of step. The selected
 * value is named, and the group selects by equality.
 *
 * @param options the choices, in the order they should appear.
 * @param labelOf resolves one option to its visible label. A composable lambda because the
 *   labels are string resources; the option type itself is never a String, so the caller's
 *   domain values (a `ContentLanguage`, a `ThemeMode`) cannot be swapped for a raw string.
 * @param caption a line under the group, or null. Used to say out loud what a control does *not*
 *   do — the language row needs that, the theme row does not.
 */
@Composable
fun <T> ChoiceGroup(
    title: String,
    options: List<T>,
    selected: T,
    labelOf: @Composable (T) -> String,
    onSelect: (T) -> Unit,
    modifier: Modifier = Modifier,
    caption: String? = null,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = title,
            style = UstadType.label,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        // A group, so a screen reader announces "1 of 3" rather than three unrelated buttons.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectableGroup(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            // `key` is explicit because these are generated from a list: without it the slots are
            // positional, and adding a theme option to the middle of the enum would let Compose
            // reuse the wrong slot's state. The value is the option itself, which is an enum
            // constant and therefore stable across recompositions.
            options.forEach { option ->
                key(option) {
                    ChoiceOption(
                        label = labelOf(option),
                        chosen = option == selected,
                        onClick = { onSelect(option) },
                    )
                }
            }
        }
        if (caption != null) {
            Text(
                text = caption,
                style = UstadType.caption,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

/**
 * One option.
 *
 * A **`RowScope` extension, for the same reason `AcUstadBottomNav.NavItem` is one** (trap 13):
 * `Modifier.weight` is declared inside `RowScope`, not on `Modifier`, so the weighting is done
 * here — where the receiver is in scope — rather than by a caller passing a `Modifier` in. A
 * plain top-level composable that took a `Modifier` and called `modifier.weight(1f)` in its own
 * body would not compile, and the failure is invisible in review because the rest of the app
 * makes the identical call and compiles.
 *
 * The border stays on both states. Only the fill and the text colour change, so the control keeps
 * its place in the layout and its 2dp ink edge in sunlight, where a bare colour change on a white
 * background disappears.
 */
@Composable
private fun RowScope.ChoiceOption(
    label: String,
    chosen: Boolean,
    onClick: () -> Unit,
) {
    val container = if (chosen) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.background
    }
    val content = if (chosen) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Box(
        modifier = Modifier
            .weight(1f)
            .height(TOUCH_MIN_DP)
            .background(container)
            .border(
                border = BorderStroke(BORDER_DP, MaterialTheme.colorScheme.outline),
                shape = RoundedCornerShape(CORNER_DP),
            )
            .selectable(selected = chosen, role = Role.RadioButton, onClick = onClick)
            .semantics { contentDescription = label },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            style = UstadType.label,
            color = content,
            maxLines = 1,
        )
    }
}

/** 48dp: the same floor as every other tap target. Used with gloves. (RULE 16) */
private val TOUCH_MIN_DP = 48.dp

/** 2dp ink border, as on every card, row and chip. Never 1dp. (PHASE_8 §6) */
private val BORDER_DP = 2.dp

/** 2dp, matching SeverityChip. Sharp corners, never a pill. (RULE 9) */
private val CORNER_DP = 2.dp
