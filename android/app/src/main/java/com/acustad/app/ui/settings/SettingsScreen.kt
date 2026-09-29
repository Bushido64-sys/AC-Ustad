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
            SourcesPanel(state)
            AboutPanel(state)
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

/** What is covered, so "my model is missing" has a real answer. (PHASE_7 §6) */
@Composable
private fun SourcesPanel(state: SettingsState) {
    val meta = (state as? SettingsState.Ready)?.meta ?: return
    BorderedPanel(modifier = Modifier.fillMaxWidth()) {
        PanelColumn {
            PanelHeading(stringResource(R.string.settings_sources_title))
            Text(
                text = stringResource(
                    R.string.settings_sources_coverage,
                    meta.brandCount,
                    meta.seriesCount,
                    meta.codeCount,
                ),
                style = UstadType.listRow,
            )
            MutedLine(stringResource(R.string.settings_sources_note))
        }
    }
}

/**
 * Three lines, per PHASE_7 §6. The third appears only when the size was actually measured.
 *
 * "It uploads nothing" is not a slogan here — it is the reason this screen carries no website
 * and no support address.
 */
@Composable
private fun AboutPanel(state: SettingsState) {
    val bytes = (state as? SettingsState.Ready)?.dataBytes
    BorderedPanel(modifier = Modifier.fillMaxWidth()) {
        PanelColumn {
            PanelHeading(stringResource(R.string.settings_about_title))
            Text(text = stringResource(R.string.settings_about_what), style = UstadType.caption)
            Text(text = stringResource(R.string.settings_about_offline), style = UstadType.caption)
            val size = formatDataSize(bytes)
            if (size != null) {
                Text(
                    text = stringResource(R.string.settings_about_size, size),
                    style = UstadType.caption,
                )
            }
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

/**
 * A human size for the knowledge base, or null when nothing measured one.
 *
 * Integer arithmetic throughout. `String.format("%.1f", …)` is shorter, and it also follows the
 * device locale — a phone set to a language that writes `8,8` would put a comma inside a
 * sentence that is otherwise English. (RULE 13)
 *
 * Null in, null out: the caller omits the clause rather than asserting a size that was never
 * measured. 0 is not a size, it is the absence of a file. (trap 5)
 */
fun formatDataSize(bytes: Long?): String? {
    if (bytes == null || bytes <= 0) return null
    return when {
        bytes < KIB -> "$bytes bytes"
        bytes < MIB -> "${bytes / KIB} kB"
        else -> {
            // Tenths of a megabyte, with no floating point involved.
            val tenths = (bytes * 10) / MIB
            "${tenths / 10}.${tenths % 10} MB"
        }
    }
}

private const val KIB = 1024L
private const val MIB = 1024L * 1024L
