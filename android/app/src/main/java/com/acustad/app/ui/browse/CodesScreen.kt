package com.acustad.app.ui.browse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.acustad.app.model.CodeSummary
import com.acustad.app.ui.common.BorderedRow
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
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
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
                        EmptyState(message = noMatch)
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
