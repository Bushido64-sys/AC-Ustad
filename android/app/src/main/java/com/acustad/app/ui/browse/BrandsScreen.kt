package com.acustad.app.ui.browse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.acustad.app.ui.common.BorderedRow
import com.acustad.app.ui.common.EmptyState
import com.acustad.app.ui.common.SearchField
import com.acustad.app.ui.common.ShowAllRow
import com.acustad.app.ui.theme.UstadType

/**
 * The brands in one category.
 *
 * Per DESIGN.md §4.2: a search field that searches **brand names only**, rows of 56dp+ with a
 * 2dp ink border, the count in mono on the right, sorted by code count descending, and the
 * four zero-code brands in each category visible but muted rather than hidden — they are
 * researched brands with nothing published, which is a fact about the world, not an error.
 *
 * There is no grid and no logo. A logo per brand would mean 62 trademarks and either megabytes
 * or a network call, and the app has neither (ASSETS.md §3).
 */
@Composable
fun BrandsScreen(
    onBrandClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BrandsViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val query by viewModel.query.collectAsStateWithLifecycle()

    val noMatch = stringResource(R.string.empty_no_brand_match, query)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(PaddingValues(bottom = 8.dp)),
    ) {
        SearchField(
            value = query,
            onValueChange = viewModel::onQueryChange,
            placeholderRes = R.string.search_hint_brands,
            onClear = viewModel::onClearQuery,
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(items = state.brands, key = { it.id }) { brand ->
                BrandRow(
                    name = brand.name,
                    codeCount = brand.codeCount,
                    onClick = { onBrandClick(brand.id) },
                )
            }
            if (state.canShowAll) {
                item(key = "show-all") {
                    ShowAllRow(shown = state.brands.size, total = state.total, onClick = viewModel::showAll)
                }
            }
            if (state.brands.isEmpty() && query.isNotBlank()) {
                item(key = "empty") { EmptyState(message = noMatch) }
            }
            if (state.brands.isEmpty() && query.isBlank()) {
                // No query, no rows: the database did not load. That is an error, not a
                // search miss, and it must not be worded like a search miss.
                item(key = "empty-all") {
                    EmptyState(message = stringResource(R.string.empty_no_brands))
                }
            }
        }
    }
}

/**
 * One brand: name on the left, code count in mono on the right, 56dp minimum so it can be
 * tapped with gloves on. The count is the reason the row exists — it tells a technician whether
 * this brand is worth entering.
 */
@Composable
private fun BrandRow(name: String, codeCount: Int, onClick: () -> Unit) {
    val countText = if (codeCount == 1) {
        stringResource(R.string.unit_codes_one)
    } else {
        stringResource(R.string.unit_codes_many, codeCount)
    }
    val description = "$name, $countText"

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
                text = name,
                style = UstadType.listRow,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f, fill = false),
                // A zero-code brand is real but not worth entering; the muted colour and the
                // count say so, and the empty state on the next screen explains it properly.
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
