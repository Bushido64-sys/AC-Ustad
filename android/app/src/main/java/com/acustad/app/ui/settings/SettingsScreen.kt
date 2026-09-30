package com.acustad.app.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.acustad.app.R
import com.acustad.app.model.ContentLanguage
import com.acustad.app.model.ThemeMode
import com.acustad.app.ui.common.BorderedPanel
import com.acustad.app.ui.common.ChoiceGroup
import com.acustad.app.ui.common.ContentLanguageToggle
import com.acustad.app.ui.common.PanelColumn
import com.acustad.app.ui.theme.UstadType

/**
 * Settings: two controls a technician sets once, and the facts about the data behind them.
 * Nothing here is a feature and nothing here is decorative. (DESIGN.md §4.7)
 *
 * The order is the argument the screen makes. The two switches come first, because they are the
 * only things here a user can act on. The data version comes next, because it is what makes them
 * worth having. Sources and About come last and read as what they are.
 *
 * **Plain rows, 2dp borders, no illustration, no social links** — DESIGN.md §4.7. There is no
 * rate button and no "send feedback", because there is nowhere for either to go: the app has no
 * address to send anything to, and adding one would mean adding the network that RULE 14 bans.
 *
 * Every number on this screen is read from the database at runtime. None of them is typed into
 * `strings.xml`, because a data release would move them and a string resource would not follow.
 */
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = viewModel(),
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val language by viewModel.language.collectAsStateWithLifecycle()
    val theme by viewModel.theme.collectAsStateWithLifecycle()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            LanguagePanel(language, viewModel::setLanguage)
            ThemePanel(theme, viewModel::setTheme)
            DataPanel(state, viewModel::load)
            AboutPanel()
        }
    }
}

@Composable
private fun LanguagePanel(
    language: ContentLanguage,
    onSelect: (ContentLanguage) -> Unit,
) {
    BorderedPanel(modifier = Modifier.fillMaxWidth()) {
        PanelColumn {
            ContentLanguageToggle(selected = language, onSelect = onSelect)
        }
    }
}

@Composable
private fun ThemePanel(theme: ThemeMode, onSelect: (ThemeMode) -> Unit) {
    BorderedPanel(modifier = Modifier.fillMaxWidth()) {
        PanelColumn {
            ChoiceGroup(
                title = stringResource(R.string.settings_theme_title),
                options = ThemeMode.entries,
                selected = theme,
                labelOf = { option ->
                    stringResource(
                        when (option) {
                            ThemeMode.SYSTEM -> R.string.theme_system
                            ThemeMode.LIGHT -> R.string.theme_light
                            ThemeMode.DARK -> R.string.theme_dark
                        }
                    )
                },
                onSelect = onSelect,
            )
        }
    }
}

/**
 * The data block, and the only part of this screen that can fail.
 *
 * A failure is shown **in place**, as one line inside the panel, with the rest of the screen
 * untouched. Blanking Settings because a query threw would take away the language and theme
 * controls — the two things that demonstrably work — in order to report a problem with the third.
 */
@Composable
private fun DataPanel(state: SettingsState, onRetry: () -> Unit) {
    BorderedPanel(modifier = Modifier.fillMaxWidth()) {
        PanelColumn {
            PanelHeading(stringResource(R.string.settings_data_title))
            when (state) {
                is SettingsState.Loading -> MutedLine(stringResource(R.string.loading))

                is SettingsState.Failed -> {
                    MutedLine(stringResource(R.string.settings_data_unavailable))
                    // A `Row`, not `Modifier.align(Alignment.Start)`.
                    //
                    // `align` is declared inside `ColumnScope`, and this lambda is the *content*
                    // of `PanelColumn` — which is itself an ordinary composable that opens the
                    // Column one level down. So there is no `ColumnScope` receiver in scope here
                    // at all, and `align` does not resolve. Same family as trap 13: the rest of
                    // the app makes this exact call and compiles, because those call sites sit
                    // directly inside a `Column { }`.
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        TextButton(
                            onClick = onRetry,
                            modifier = Modifier.semantics { contentDescription = "Try again" },
                        ) {
                            Text(
                                text = stringResource(R.string.action_try_again),
                                style = UstadType.label,
                            )
                        }
                    }
                }

                is SettingsState.Ready -> {
                    val meta = state.meta
                    Text(
                        text = stringResource(R.string.settings_data_version, meta.kbVersion),
                        style = UstadType.listRow,
                    )
                    // `built_at` is a full ISO timestamp and the design asks for the build
                    // *date*. Only the date is shown, and only when the stored string actually
                    // has one — see `formatBuiltDate`.
                    val built = formatBuiltDate(meta.builtAt)
                    if (built != null) {
                        MutedLine(stringResource(R.string.settings_data_built, built))
                    }
                }
            }
        }
    }
}

/**
 * What this app is, what it does for you, and what it does not do. **Removed 2026-09-29**:
 * the coverage panel that used to sit here, and the line stating the size of the shipped
 * database.
 *
 * Both are gone on the user's instruction and it is worth recording why that is a real change
 * rather than a cosmetic one. `PHASE_7` §6 asks Settings to show coverage "so 'my model is
 * missing' is answerable by pointing at a real gap", and the size was a factual measure of what
 * the app carries. Neither is false, but neither is what someone opens Settings to find, and a
 * screen listing 62 brands and 320 model lines in the middle of a phone is a number about the
 * app rather than a fact about their machine. The data version stays: that one tells a
 * technician whether the answers they are reading are current, which is the one data fact that
 * earns its place on this screen.
 *
 * The remaining copy introduces the app and then says what it does for the person reading it.
 * No adjectives, no claims about size or coverage, and no punctuation the copy rules ban
 * (DESIGN.md §6).
 */
@Composable
private fun AboutPanel() {
    BorderedPanel(modifier = Modifier.fillMaxWidth()) {
        PanelColumn {
            PanelHeading(stringResource(R.string.settings_about_title))
            Text(text = stringResource(R.string.settings_about_what), style = UstadType.caption)
            PanelHeading(stringResource(R.string.settings_about_helps_title))
            Text(text = stringResource(R.string.settings_about_helps_1), style = UstadType.caption)
            Text(text = stringResource(R.string.settings_about_helps_2), style = UstadType.caption)
            Text(text = stringResource(R.string.settings_about_helps_3), style = UstadType.caption)
            Text(text = stringResource(R.string.settings_about_helps_4), style = UstadType.caption)
            Text(text = stringResource(R.string.settings_about_privacy), style = UstadType.caption)
        }
    }
}

@Composable
private fun PanelHeading(text: String) {
    Text(
        text = text,
        style = UstadType.label,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Composable
private fun MutedLine(text: String) {
    Text(
        text = text,
        style = UstadType.caption,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

/**
 * The date portion of `meta.built_at`, or null when there is not one to show.
 *
 * The stored value is a full ISO timestamp (`2026-09-28T05:48:34Z`) and DESIGN.md §4.7 asks for
 * the build **date**. `take` rather than `substring`, so a value shorter than ten characters
 * yields null instead of throwing — and the screen then omits the line rather than rendering a
 * truncated date as though it were whole. (trap 5)
 *
 * The comparison is `>=` rather than `>`, so a value that is *already* a bare `YYYY-MM-DD` is
 * passed through as-is. Requiring more than ten characters would silently drop the one format
 * that is already correct.
 */
fun formatBuiltDate(raw: String?): String? {
    val trimmed = raw?.trim().orEmpty()
    return if (trimmed.length >= BUILT_DATE_LENGTH) trimmed.take(BUILT_DATE_LENGTH) else null
}

private const val BUILT_DATE_LENGTH = 10
