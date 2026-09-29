package com.acustad.app.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.acustad.app.R
import com.acustad.app.model.ContentLanguage

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
 *
 * This is a wrapper rather than the control: [ChoiceGroup] draws it, and the theme row on the
 * Settings screen draws the same pixels. The labels stay here rather than at the call site so
 * that "EN" and "UR" are defined once, next to the reason they are the knowledge base's own
 * words and not a translation of our own.
 */
@Composable
fun ContentLanguageToggle(
    selected: ContentLanguage,
    onSelect: (ContentLanguage) -> Unit,
    modifier: Modifier = Modifier,
) {
    ChoiceGroup(
        title = stringResource(R.string.language_title),
        options = ContentLanguage.entries,
        selected = selected,
        labelOf = { option ->
            stringResource(
                when (option) {
                    ContentLanguage.EN -> R.string.language_en
                    ContentLanguage.UR -> R.string.language_ur
                }
            )
        },
        onSelect = onSelect,
        caption = stringResource(R.string.language_content_only),
        modifier = modifier,
    )
}
