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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.acustad.app.ui.common.BorderedPanel
import com.acustad.app.ui.common.BorderedRow
import com.acustad.app.ui.common.EmptyState
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

    val noMatch = stringResource(R.string.empty_no_model_match, query)

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

@Composable
private fun BrandNotes(notes: String, expanded: Boolean, onToggle: () -> Unit) {
    val label = if (expanded) {
        stringResource(R.string.action_hide_details)
    } else {
        stringResource(R.string.action_show_details)
    }
    BorderedPanel(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            if (expanded) {
                Text(
                    text = notes,
                    style = UstadType.body,
                    // These notes are editorial, never Roman Urdu: they explain what was
                    // researched, not how to fix anything.
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            TextButton(onClick = onToggle, modifier = Modifier.heightIn(min = 48.dp)) {
                Text(text = label, style = UstadType.label)
            }
        }
    }
}

@Composable
private fun SeriesRow(modelName: String, codeCount: Int, onClick: () -> Unit) {
    val countText = if (codeCount == 1) {
        stringResource(R.string.unit_series_one)
    } else {
        stringResource(R.string.unit_series_many, codeCount)
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
