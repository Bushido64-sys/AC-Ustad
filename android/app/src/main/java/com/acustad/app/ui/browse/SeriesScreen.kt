package com.acustad.app.ui.browse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.acustad.app.ads.NativeAdCard
import com.acustad.app.ads.rememberNativeAdPool
import com.acustad.app.ui.common.BorderedRow
import com.acustad.app.ui.common.EmptyState
import com.acustad.app.ui.common.RaisedPanel
import com.acustad.app.ui.common.SearchField
import com.acustad.app.ui.theme.UstadType

/**
 * The model lines of one brand, per DESIGN.md §4.3.
 *
 * Rows are sorted by code count descending, the model name is always English (a model number is
 * read in English by habit — RULE 13), and the count is in mono so a column of them lines up.
 */
@Composable
fun SeriesScreen(
    onSeriesClick: (SeriesTarget) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SeriesViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val query by viewModel.query.collectAsStateWithLifecycle()
    val notesExpanded by viewModel.notesExpanded.collectAsStateWithLifecycle()
    val listState = rememberListStateFor(viewModel.brandId)

    val noMatch = stringResource(R.string.empty_no_model_match, query)
    val nativePool = rememberNativeAdPool(size = 1)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(PaddingValues(bottom = 8.dp)),
    ) {
        SearchField(
            value = query,
            onValueChange = viewModel::onQueryChange,
            placeholderRes = R.string.search_hint_models,
            onClear = viewModel::onClearQuery,
        )

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            val notes = state.notes
            if (notes != null && notes.isNotBlank()) {
                item(key = "notes") {
                    BrandNotes(
                        notes = notes,
                        expanded = notesExpanded,
                        onToggle = viewModel::toggleNotes,
                    )
                }
            }

            items(items = state.series, key = { it.uid }) { series ->
                SeriesRow(
                    modelName = series.name,
                    codeCount = series.codeCount,
                    onClick = {
                        onSeriesClick(
                            SeriesTarget(
                                seriesId = series.seriesId,
                                brandId = series.brandId,
                            )
                        )
                    },
                )
            }

            if (state.series.isEmpty() && query.isNotBlank()) {
                item(key = "empty") { EmptyState(message = noMatch) }
            }
            if (state.series.isEmpty() && query.isBlank()) {
                item(key = "empty-all") {
                    EmptyState(message = stringResource(R.string.empty_no_brands))
                }
            }

            // One native card at the bottom, shaped like the rows above it but
            // badged by construction (NativeAdCard). TEMPORARY: unfilled shows
            // its state as text on DEBUG builds (no adb on the test phone).
            if (state.series.isNotEmpty() && query.isBlank()) {
                val ad = nativePool.adFor(0)
                if (ad != null) {
                    item(key = "native-bottom") {
                        NativeAdCard(ad = ad)
                    }
                }
            }
        }
    }
}

/**
 * The two fields the codes screen needs to scope its queries correctly.
 *
 * Names are deliberately absent: they are fetched by the destination from the database, because
 * a route segment cannot safely contain a model name with slashes and brackets.
 */
data class SeriesTarget(
    val seriesId: String,
    val brandId: String,
)

/**
 * The brand's own `notes_en`, expanded on request.
 *
 * **Level 2 — raised**, per PHASE_11 §3.4. It is the one place on this screen where the
 * research notes are quoted rather than summarised, and a quotation is exactly what a raised
 * surface means in this app. It also sets the brand's model lines apart from the rows, which is
 * useful, because the list below it *is* the screen's real content.
 *
 * **The text is full ink, not muted, and that is not a preference.** `ink_muted` on
 * `surface_alt` measures 4.15:1 — under the 4.5 AA text bar — in light mode only. It is 6.93:1
 * in dark, so a dark-mode screenshot would never have caught it. The rule on `RaisedPanel` is
 * "no muted text inside"; this is the first place that rule actually bites, and the fix is full
 * ink rather than moving the text out, because the whole block is the quotation.
 *
 * The notes stay English in both content languages: they explain what was researched, not how
 * to fix anything, so they are not code content and RULE 13 does not reach them.
 */
@Composable
private fun BrandNotes(notes: String, expanded: Boolean, onToggle: () -> Unit) {
    val label = if (expanded) {
        stringResource(R.string.action_hide_details)
    } else {
        stringResource(R.string.action_show_details)
    }
    RaisedPanel(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            if (expanded) {
                Text(
                    text = notes,
                    style = UstadType.body,
                )
            }
            TextButton(onClick = onToggle, modifier = Modifier.heightIn(min = 48.dp)) {
                Text(text = label, style = UstadType.label)
            }
        }
    }
}

/**
 * One model line: name on the left, **code count** in mono on the right.
 *
 * The number here is `series.code_count` — how many codes this model publishes — and the label
 * says codes. It used to say "N models" on a row that was already a model, which told the
 * technician nothing: the question on this screen is which model has the codes, so that is what
 * the right-hand number answers (the brand row one level up counts models instead).
 */
@Composable
private fun SeriesRow(modelName: String, codeCount: Int, onClick: () -> Unit) {
    val countText = if (codeCount == 1) {
        stringResource(R.string.unit_codes_one)
    } else {
        stringResource(R.string.unit_codes_many, codeCount)
    }
    val description = "$modelName, $countText"

    BorderedRow(
        modifier = Modifier
            .fillMaxWidth()
            .semantics(mergeDescendants = true) { contentDescription = description },
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = modelName,
                style = UstadType.listRow,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f, fill = false),
                color = if (codeCount == 0) {
                    MaterialTheme.colorScheme.onSurfaceVariant
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
            )
            Text(
                text = countText,
                style = UstadType.count,
                maxLines = 1,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .clearAndSetSemantics { },
            )
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
