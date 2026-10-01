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
import com.acustad.app.BuildConfig
import com.acustad.app.R
import com.acustad.app.model.ContentLanguage
import com.acustad.app.model.ThemeMode
import com.acustad.app.ui.common.BorderedPanel
import com.acustad.app.ui.common.ChoiceGroup
import com.acustad.app.ui.common.ContentLanguageToggle
import com.acustad.app.ui.common.PanelColumn
import com.acustad.app.ui.theme.UstadType

/**
 * Settings: what the app is, the two controls a technician sets once, and the facts
 * behind them. Nothing here is a feature and nothing here is decorative. (DESIGN.md §4.7)
 *
 * The order is the argument the screen makes. The title names the screen, like every
 * other screen. Preferences come first, because they are the only things here a user
 * can act on. About comes next, carrying the one merged version line — data version
 * from the database, app version from the package, neither typed in. Privacy and
 * Licenses close the screen and read as what they are.
 *
 * **Plain rows, 2dp borders, no illustration, no social links** — DESIGN.md §4.7. There is no
 * rate button and no "send feedback", because there is nowhere for either to go: the app has no
 * address to send anything to, and adding one would mean adding the network that RULE 14 bans.
 *
 * Every version on this screen is read at runtime. None of them is typed into
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
            Text(
                text = stringResource(R.string.settings_title),
                style = UstadType.title,
            )
            PreferencesPanel(language, viewModel::setLanguage, theme, viewModel::setTheme)
            AboutPanel(state, viewModel::load)
            PrivacyPanel()
            LicensesPanel()
        }
    }
}

/**
 * The two controls a technician sets once, grouped in one panel under one heading.
 *
 * They used to be two identical floating cards with no grouping, which read as stray
 * blocks once the rest of the screen joined them. One panel, one heading, same
 * controls — the controls themselves are unchanged.
 */
@Composable
private fun PreferencesPanel(
    language: ContentLanguage,
    onLanguage: (ContentLanguage) -> Unit,
    theme: ThemeMode,
    onTheme: (ThemeMode) -> Unit,
) {
    BorderedPanel(modifier = Modifier.fillMaxWidth()) {
        PanelColumn {
            PanelHeading(stringResource(R.string.settings_prefs_title))
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                ContentLanguageToggle(selected = language, onSelect = onLanguage)
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
                    onSelect = onTheme,
                )
            }
        }
    }
}

/**
 * What this app is, what it does for you, and the one merged version line.
 *
 * The old Data panel is gone: it showed the data version and the build date as a
 * block of its own, sitting between the controls and About where it belonged to
 * neither. Its one load-bearing fact — the data version, which tells a technician
 * whether the answers they read are current — survives as the last line here, joined
 * with the app version. The build date is dropped everywhere: it told nobody what
 * to do.
 *
 * A failed read is shown **in place**, as one line, with the rest of the screen
 * untouched. Blanking Settings because a query threw would take away the controls
 * that demonstrably work in order to report a problem with a version line.
 */
@Composable
private fun AboutPanel(state: SettingsState, onRetry: () -> Unit) {
    BorderedPanel(modifier = Modifier.fillMaxWidth()) {
        PanelColumn {
            PanelHeading(stringResource(R.string.settings_about_title))
            Text(text = stringResource(R.string.settings_about_what), style = UstadType.caption)
            PanelHeading(stringResource(R.string.settings_about_helps_title))
            Text(text = stringResource(R.string.settings_about_helps_1), style = UstadType.caption)
            Text(text = stringResource(R.string.settings_about_helps_2), style = UstadType.caption)
            Text(text = stringResource(R.string.settings_about_helps_3), style = UstadType.caption)
            Text(text = stringResource(R.string.settings_about_helps_4), style = UstadType.caption)
            when (state) {
                is SettingsState.Loading -> MutedLine(stringResource(R.string.loading))

                is SettingsState.Failed -> {
                    MutedLine(stringResource(R.string.settings_version_unavailable))
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
                    MutedLine(
                        stringResource(
                            R.string.settings_about_version,
                            state.meta.kbVersion,
                            appVersion(),
                        )
                    )
                }
            }
        }
    }
}

/**
 * The privacy promise, in the same words as PRIVACY.md.
 *
 * True today and written so it stays true: no account, no permissions, no network,
 * nothing uploaded. The day ads land (see the ads document) this
 * panel is rewritten first — the "no network" line goes the same commit the SDK
 * arrives, never before.
 */
@Composable
private fun PrivacyPanel() {
    BorderedPanel(modifier = Modifier.fillMaxWidth()) {
        PanelColumn {
            PanelHeading(stringResource(R.string.settings_privacy_title))
            Text(text = stringResource(R.string.settings_privacy_1), style = UstadType.caption)
            Text(text = stringResource(R.string.settings_privacy_2), style = UstadType.caption)
        }
    }
}

/**
 * Who owns the app and whose code it stands on.
 */
@Composable
private fun LicensesPanel() {
    BorderedPanel(modifier = Modifier.fillMaxWidth()) {
        PanelColumn {
            PanelHeading(stringResource(R.string.settings_licenses_title))
            Text(text = stringResource(R.string.settings_licenses_app), style = UstadType.caption)
            Text(text = stringResource(R.string.settings_licenses_oss), style = UstadType.caption)
        }
    }
}

/**
 * The app version as the technician reads it: versionName and versionCode together.
 *
 * Both come from the build config, never typed in — a data release moves the data
 * version above, an app release moves this one, and neither can go quietly stale.
 */
private fun appVersion(): String = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"

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
