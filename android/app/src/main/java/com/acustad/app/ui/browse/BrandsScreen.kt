package com.acustad.app.ui.browse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.Alignment
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
    val teaching by viewModel.teaching.collectAsStateWithLifecycle()
    // Keyed on the category, so AC and Inverter do not share a scroll position.
    val listState = rememberListStateFor(viewModel.category.name)

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
            state = listState,
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
                item(key = "empty") { DeadEndState(query, teaching, viewModel::onClearQuery) }
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
 * "No results" on the brands screen, which is two different situations wearing one hat.
 *
 * A technician who typed a **brand name** that does not exist gets a plain miss, and that is the
 * whole answer. A technician who typed a **code** gets the miss *and* the reason it is a miss —
 * because that screen is one level above where codes live, and saying so turns a dead end into
 * the app explaining itself. (DESIGN.md §4.2, PHASE_5_SEARCH.md §5)
 *
 * The action is always the same and it is deliberately modest: show the full brand list again.
 * **It is not "search models instead"** — that was the guide's suggestion, and it would not
 * work. Models are scoped to a brand (RULE 2), so there is no model search to send them to until
 * they have picked a brand; an action that jumped to a search they cannot perform is a worse
 * dead end than the one it replaced. Clearing the query is the one step that is always available
 * and always right.
 */
@Composable
private fun DeadEndState(
    query: String,
    teaching: BrandSearchTeaching?,
    onClearQuery: () -> Unit,
) {
    when (teaching) {
        is BrandSearchTeaching.Ambiguous -> EmptyState(
            message = stringResource(R.string.empty_no_brand_match, query),
            detail = stringResource(R.string.teach_ambiguous, teaching.code, teaching.brandCount),
            actionLabel = stringResource(R.string.teach_action),
            onAction = onClearQuery,
        )

        is BrandSearchTeaching.OneBrand -> EmptyState(
            message = stringResource(R.string.empty_no_brand_match, query),
            // The name can be empty only if the query changed between the lookup and this frame,
            // which the view model's equality check prevents. A blank brand is shown as the code
            // itself rather than as an empty gap, because a sentence with a hole in it reads
            // like a bug. (trap 5)
            detail = stringResource(
                R.string.teach_one_brand,
                teaching.code,
                teaching.brandName.ifBlank { teaching.code },
            ),
            actionLabel = stringResource(R.string.teach_action),
            onAction = onClearQuery,
        )

        // null while the lookup is in flight, and `None` for a query that is not a code. Both
        // show the plain miss and nothing else: an explanation that appears late and unbidden
        // is worse than none.
        else -> EmptyState(message = stringResource(R.string.empty_no_brand_match, query))
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

/**
 * A list state that survives navigation.
 *
 * DESIGN.md §4.3: *"Back returns to the brands list with its query and scroll position intact."*
 * Neither was kept, and it is the single most irritating thing a list app can get wrong: a
 * technician has scrolled to one brand out of 62, tapped the wrong row, pressed back, and is now
 * at the top of an alphabet they were halfway down.
 *
 * `rememberSaveable`, not `remember`, and the difference is the whole point. `remember` dies with
 * the composition, and a screen that is merely off-screen is not destroyed — but a
 * `NavBackStackEntry` that is popped **is**, so `remember` would lose the position exactly when
 * the app returns. `rememberSaveable` survives that, and also survives a configuration change,
 * which matters because this app is used in a van.
 *
 * The key is the route argument, not a constant: three different lists share this function, and
 * a shared key would restore Carrier's scroll position onto Dawlance's list.
 *
 * The saved value is the list state's own `firstVisibleItemIndex` / `firstVisibleItemScrollOffset`
 * pair, which Compose saves for free. Nothing is measured or computed here.
 */
@Composable
private fun rememberListStateFor(key: String): LazyListState =
    rememberSaveable(saver = LazyListState.Saver, key) { LazyListState() }
