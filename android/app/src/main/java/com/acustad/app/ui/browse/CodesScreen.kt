package com.acustad.app.ui.browse

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.acustad.app.R
import com.acustad.app.model.CodeSummary
import com.acustad.app.ui.common.BorderedRow
import com.acustad.app.ui.common.hardShadow
import com.acustad.app.ui.common.EmptyState
import com.acustad.app.ui.common.SearchField
import com.acustad.app.ui.common.SeverityChip
import com.acustad.app.ui.common.Severity
import com.acustad.app.ui.common.indicatorVisuals
import com.acustad.app.ui.common.severityVisuals
import com.acustad.app.ui.theme.UstadType

/**
 * The codes of one model line, per DESIGN.md §4.4.
 *
 * A **serialised list, never a grid**: code strings run from `P003` to a 40-character blink
 * descriptor, and a fixed-width grid would clip them. Rows grow to two lines.
 *
 * Layout per row: severity chip on the left, code in IBM Plex Mono, title beneath. The chip is
 * the *first* thing in the row because 60% of faults are `stop_pro` and a technician scanning
 * for something serious is looking for the one that is not tinted.
 */
@Composable
fun CodesScreen(
    onCodeClick: (CodeSummary) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CodesViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val query by viewModel.query.collectAsStateWithLifecycle()
    val listState = rememberListStateFor("${viewModel.seriesId}/${viewModel.brandId}")
    val noMatch = stringResource(R.string.empty_no_code_match, query)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(PaddingValues(bottom = 8.dp)),
    ) {
        SearchField(
            value = query,
            onValueChange = viewModel::onQueryChange,
            placeholderRes = R.string.search_hint_codes,
            onClear = viewModel::onClearQuery,
            // Codes are not words, so sentence capitalisation would be wrong here.
            capitalizeWords = false,
        )

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            // DESIGN.md §4.4: *"If the query matches exactly one code, offer 'Open E6' rather
            // than making them tap a row."*
            //
            // One code, one action. The technician has typed the exact code they can see on the
            // unit, and making them then find and tap the right row out of a filtered list is a
            // tap that exists only to satisfy the layout. The row stays underneath — removing it
            // would make the result list look empty and would break muscle memory for anyone
            // already used to it.
            //
            // **Only on an exact single match, never on a prefix.** "E" can prefix-match forty
            // codes and offering "Open E" for forty codes would be a lie. This is why the test
            // is `size == 1` on the filtered list and not on the query.
            if (query.isNotBlank() && state.codes.size == 1) {
                item(key = "open-single") {
                    OpenSingleResult(
                        code = state.codes.first().code,
                        onClick = { onCodeClick(state.codes.first()) },
                    )
                }
            }

            items(items = state.codes, key = { it.id }) { code ->
                CodeRow(
                    code = code,
                    language = state.language,
                    onClick = { onCodeClick(code) },
                )
            }
            if (state.codes.isEmpty() && !state.isSearching) {
                item(key = "empty") {
                    if (query.isNotBlank()) {
                        // The third step of `searchCodes` — this model's own descriptions —
                        // produces no row of its own, so a technician reading a bare "no code
                        // matches" cannot tell it ran. `isDescription` is the same carve-out the
                        // brands screen uses: a fault description deserves saying what was
                        // searched, a code (`E6`) only needs the headline.
                        val detail = if (isDescription(query)) {
                            stringResource(R.string.empty_no_code_match_detail, viewModel.seriesName)
                        } else {
                            null
                        }
                        EmptyState(message = noMatch, detail = detail)
                    } else {
                        EmptyState(
                            message = stringResource(
                                R.string.empty_series_no_codes,
                                viewModel.seriesName,
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CodeRow(code: CodeSummary, language: com.acustad.app.model.ContentLanguage, onClick: () -> Unit) {
    val title = code.title(language)
    // A non-fault row (569 of 4,418) is an indicator or a parameter, not a breakdown, so it gets
    // a muted rail and the word INDICATOR rather than a severity chip.
    val visuals = if (code.isFault) {
        severityVisuals(Severity.from(code.severity), language)
    } else {
        indicatorVisuals(language)
    }
    val description = buildString {
        append(visuals.label)
        append(", ")
        append(code.code)
        if (title != null) {
            append(", ")
            append(title)
        }
    }

    BorderedRow(
        modifier = Modifier
            .fillMaxWidth()
            .semantics(mergeDescendants = true) { contentDescription = description },
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(48.dp)
                    .background(
                        if (code.isFault) {
                            MaterialTheme.colorScheme.outline
                        } else {
                            MaterialTheme.colorScheme.outlineVariant
                        }
                    )
                    .clearAndSetSemantics { },
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                SeverityChip(
                    label = visuals.label,
                    background = visuals.background,
                    contentColor = visuals.content,
                    border = visuals.border,
                )
                Text(
                    text = code.code,
                    style = UstadType.codeList,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                if (title != null) {
                    Text(
                        text = title,
                        style = UstadType.body,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

/**
 * A list state that survives navigation.
 *
 * DESIGN.md §4.3: *"Back returns to the brands list with its query and scroll position intact."*
 * Neither was kept, and it is the most irritating thing a list app can get wrong: a technician
 * has scrolled to one brand out of 62, taps the wrong row, presses back, and is at the top of a
 * list they were halfway down.
 *
 * `rememberSaveable`, not `remember`. `remember` dies with the composition, and a
 * `NavBackStackEntry` that has been **popped** is destroyed — which is exactly when the app
 * returns. `rememberSaveable` survives that, and a configuration change too, which matters
 * because this app is used in a van.
 *
 * @param key the route argument that identifies *which* list this is. Three lists share this
 *   function, and a shared key would restore Carrier's scroll position onto Dawlance's.
 */
@Composable
private fun rememberListStateFor(key: String): LazyListState =
    rememberSaveable(key, saver = LazyListState.Saver) { LazyListState() }

/**
 * "Open E6" — the single shortcut row for an exact one-code match.
 *
 * A **primary** affordance, so it is the one row in the app that wears the structural blue with
 * white text: exactly one thing on this screen is the thing the user asked for, and marking it
 * that way means they never have to read the list to know it. White on `blue_600` is 5.87:1,
 * measured. (RULE 7: one of only two fills permitted to carry white.)
 *
 * `hardShadow` is allowed here and here alone on this screen — RULE 9 permits a shadow on
 * primary actions, and this is the only primary action in the app. The same 3dp 3dp 0 offset the
 * selected nav indicator uses, so the app has exactly one shadow language.
 */
@Composable
private fun OpenSingleResult(code: String, onClick: () -> Unit) {
    val label = stringResource(R.string.action_open_code, code)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .hardShadow()
            .background(MaterialTheme.colorScheme.primary)
            .clickable(onClick = onClick)
            .semantics(mergeDescendants = true) { contentDescription = label }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = label,
            style = UstadType.label,
            color = MaterialTheme.colorScheme.onPrimary,
            maxLines = 1,
        )
    }
}
