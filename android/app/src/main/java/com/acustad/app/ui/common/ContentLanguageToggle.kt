package com.acustad.app.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.acustad.app.R
import com.acustad.app.model.ContentLanguage
import com.acustad.app.ui.theme.UstadType

/**
 * The EN / UR content toggle.
 *
 * **It switches content, not the interface** — and that is the whole subtlety of RULE 13. The
 * code title, meaning, causes, fix steps, notes and the severity/confidence words all change.
 * Every UI label, every brand name and every model number stay English, because a technician
 * reads "Growatt" and "MOD 3-15KTL3-X" in English by habit and needs the *explanation* in their
 * own speech. There is no `values-ur` strings file and this control does not want one.
 *
 * The caption says so out loud. A control that silently ignores three quarters of the screen
 * needs to explain itself once, or it reads as broken.
 *
 * Nothing here is invented: "EN" and "UR" are the names the knowledge base itself uses for its
 * two language columns.
 *
 * The two options are **text, not icons** — a globe with a flag is the one control that has
 * solved nothing for anyone, and the whole budget for this app is five icons
 * (RULES.md RULE 11). Selected is the structural blue with white text, which is one of only two
 * fills permitted to carry white (RULE 7).
 */
@Composable
fun ContentLanguageToggle(
    selected: ContentLanguage,
    onSelect: (ContentLanguage) -> Unit,
    modifier: Modifier = Modifier,
) {
    val caption = stringResource(R.string.language_content_only)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = stringResource(R.string.language_title),
            style = UstadType.label,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        // A group, so a screen reader announces "1 of 2" instead of two unrelated buttons.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectableGroup(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            LanguageOption(
                label = stringResource(R.string.language_en),
                chosen = selected == ContentLanguage.EN,
                onClick = { onSelect(ContentLanguage.EN) },
                modifier = Modifier.weight(1f),
            )
            LanguageOption(
                label = stringResource(R.string.language_ur),
                chosen = selected == ContentLanguage.UR,
                onClick = { onSelect(ContentLanguage.UR) },
                modifier = Modifier.weight(1f),
            )
        }
        Text(
            text = caption,
            style = UstadType.caption,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun LanguageOption(
    label: String,
    chosen: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
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
        modifier = modifier
            .height(TOUCH_MIN_DP)
            .background(container)
            // The border stays on both states. Only the fill and the text colour change, so the
            // control keeps its place in the layout and its 2dp ink edge in sunlight, where a
            // bare colour change on a white background disappears.
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
