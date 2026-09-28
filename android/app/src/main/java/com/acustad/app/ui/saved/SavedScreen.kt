package com.acustad.app.ui.saved

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.acustad.app.R
import com.acustad.app.model.ContentLanguage
import com.acustad.app.model.FavouriteItem
import com.acustad.app.ui.common.BorderedRow
import com.acustad.app.ui.common.EmptyState
import com.acustad.app.ui.common.Severity
import com.acustad.app.ui.common.SeverityChip
import com.acustad.app.ui.common.StarIcon
import com.acustad.app.ui.common.severityVisuals
import com.acustad.app.ui.theme.UstadType
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * The saved list. The six codes a technician keeps needing, newest first, and nothing else. No
 * grouping, no folders, no search: it is a list of six things, not a library.
 * (PHASE_6_FAVOURITES.md §4)
 *
 * **Every name on a row comes from the join, not from the saved row.** `favourites` is
 * `(code_id, created_at)` and nothing else, so the code, the model and the brand are read back
 * from `codes`, `series` and `brands`. Nothing here is invented and no column is added to a
 * table the app does not own. (RULES.md RULE 1, RULE 5)
 *
 * Two ways to remove, because a gesture must always have a visible tap equivalent
 * (RULES.md RULE 16): swipe the row left, or tap its star. Both reach the same write, both
 * raise the same Undo bar, and both are guarded against a double tap landing twice.
 */
@Composable
fun SavedScreen(
    onCodeClick: (FavouriteItem) -> Unit,
    viewModel: SavedViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val notice by viewModel.notice.collectAsStateWithLifecycle()
    val hostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val emptyMessage = stringResource(R.string.empty_saved)
    val undoLabel = stringResource(R.string.action_undo)
    val noticeMessage = stringResource(R.string.error_could_not_save)

    fun removeWithUndo(item: FavouriteItem) {
        // A refused write is a double tap that was thrown away, so it must not raise a bar
        // offering to undo something that did not happen.
        if (!viewModel.remove(item)) return
        scope.launch {
            val result = hostState.showSnackbar(
                message = context.getString(R.string.snack_removed, item.code),
                actionLabel = undoLabel,
                duration = SnackbarDuration.Short,
            )
            if (result == SnackbarResult.ActionPerformed) viewModel.undo(item)
        }
    }

    // A failed write says so once and gets out of the way. Undo and the failure share one
    // host rather than stacking two bars over a list.
    LaunchedEffect(notice) {
        if (notice != null) {
            hostState.showSnackbar(noticeMessage)
            viewModel.dismissNotice()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        when (val s = state) {
            // The one read is a two-column table joined to three indexed ones, so loading is a
            // single frame. A bordered "loading" panel would flash; an empty frame will not.
            SavedState.Loading -> Unit
            is SavedState.Failed -> EmptyState(
                // The resource, not `s.message`: the underlying SQLite text is for a log, and
                // this screen is a designed state. Try again, because for a read that is the
                // actual fix.
                message = stringResource(R.string.error_opening_database),
                actionLabel = stringResource(R.string.action_try_again),
                onAction = viewModel::reload,
            )
            is SavedState.Ready -> if (s.items.isEmpty()) {
                EmptyState(message = emptyMessage)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(items = s.items, key = { it.codeId }) { item ->
                        SwipeToRemoveRow(onRemove = { removeWithUndo(item) }) {
                            SavedRow(
                                item = item,
                                language = s.language,
                                onClick = { onCodeClick(item) },
                                onUnsave = { removeWithUndo(item) },
                            )
                        }
                    }
                }
            }
        }

        SnackbarHost(
            hostState = hostState,
            modifier = Modifier.align(Alignment.BottomCenter),
        ) { data ->
            // Restyled to the tokens. The default Material snackbar is an inverse-surface grey,
            // which is exactly the unstyled look this design refuses, and the primary blue is
            // one of only two fills allowed to carry white text (RULES.md RULE 7).
            Snackbar(
                snackbarData = data,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                actionContentColor = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(4.dp),
            )
        }
    }
}

/**
 * A row: code in mono, its title, then model and brand, with the severity chip and a filled star
 * on the right.
 *
 * The chip is the same component the codes screen uses. A `stop_pro` chip that looked different
 * in two places would be a chip a technician has to learn twice, and 60% of all faults are
 * `stop_pro`, so this is where consistency earns its keep. (DESIGN.md §4.6)
 */
@Composable
private fun SavedRow(
    item: FavouriteItem,
    language: ContentLanguage,
    onClick: () -> Unit,
    onUnsave: () -> Unit,
) {
    val visuals = severityVisuals(Severity.from(item.severity), language)
    val title = item.title(language)
    val unstarLabel = stringResource(R.string.action_unstar)

    val description = buildString {
        append(item.code)
        append(", ")
        append(item.seriesName)
        append(", ")
        append(item.brandName)
        append(", ")
        append(visuals.label)
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
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = item.code,
                    style = UstadType.codeList,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                if (title != null) {
                    Text(
                        text = title,
                        style = UstadType.caption,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                Text(
                    text = "${item.seriesName} · ${item.brandName}",
                    style = UstadType.caption,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                SeverityChip(
                    label = visuals.label,
                    background = visuals.background,
                    contentColor = visuals.content,
                    border = visuals.border,
                )
                // The tap equivalent of the swipe: tapping a filled star on a saved row removes
                // it, which is the same control the detail screen's star is, in reverse.
                Box(
                    modifier = Modifier
                        .defaultMinSize(minWidth = 48.dp, minHeight = 48.dp)
                        .clickable(onClick = onUnsave)
                        .semantics { contentDescription = unstarLabel },
                    contentAlignment = Alignment.Center,
                ) {
                    StarIcon(
                        filled = true,
                        tint = MaterialTheme.colorScheme.primary,
                        size = 24.dp,
                    )
                }
            }
        }
    }
}

/**
 * Swipe left to remove.
 *
 * Hand-rolled rather than `SwipeToDismissBox`, for one reason that is not code size: the row has
 * to stay fully drawn while it is being dragged, with its 2dp ink border intact, and settle back
 * where it started when the swipe is abandoned. Only the *foreground* may move, and an offset
 * on a wrapper Box is the only way to say that — a dismissable box restyles and fades its own
 * content as it goes.
 *
 * The reveal behind the row is a word, not a bin icon: five icons is the whole budget for this
 * app and a trash can is not one of them. (RULES.md RULE 11) It is drawn on the blue structural
 * surface rather than the alert tint, because removing a saved row is not an alarm and the
 * signal hue is reserved for severity. (RULES.md RULE 6)
 *
 * Releasing past the threshold removes the row; releasing before it animates back. Either way
 * the offset returns to zero, so an abandoned swipe changes nothing.
 */
@Composable
private fun SwipeToRemoveRow(
    onRemove: () -> Unit,
    content: @Composable () -> Unit,
) {
    val offsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current
    val revealPx = with(density) { REVEAL_WIDTH_DP.toPx() }
    val thresholdPx = with(density) { REMOVE_THRESHOLD_DP.toPx() }
    val removeLabel = stringResource(R.string.action_remove_saved)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp)),
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.CenterEnd,
        ) {
            Text(
                text = removeLabel,
                style = UstadType.label,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(end = 16.dp),
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                // Keyed on the callback, not on Unit. A Unit key pins the first lambda this
                // ever saw, and after a locale change the Undo label in it would be the old
                // language. Dragging does not recompose — the offset is read in the layout
                // phase, not during composition — so this cannot restart a gesture in progress.
                .pointerInput(onRemove) {
                    // Accumulated in a plain local, not read back off the Animatable.
                    //
                    // `snapTo` is suspending, so the coroutine that applies each drag step has
                    // not necessarily finished when `onDragEnd` fires. Reading `offsetX.value`
                    // there is a race: a fast flick would settle on a stale, smaller offset and
                    // silently fail to remove. `total` is updated and read synchronously inside
                    // the same gesture, so the threshold is always judged on the real distance
                    // the finger travelled.
                    var total = 0f
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { change, dragAmount ->
                            change.consume()
                            total = (total + dragAmount).coerceIn(-revealPx, 0f)
                            scope.launch { offsetX.snapTo(total) }
                        },
                        onDragEnd = {
                            val past = total <= -thresholdPx
                            scope.launch {
                                if (past) onRemove() else offsetX.animateTo(0f)
                            }
                        },
                        onDragCancel = { scope.launch { offsetX.animateTo(0f) } },
                    )
                },
        ) {
            content()
        }
    }
}

/** How far the row may travel: enough to show the word, not enough to uncover its far edge. */
private val REVEAL_WIDTH_DP = 96.dp

/** Past this, a release removes the row. Short of it, the row settles back. */
private val REMOVE_THRESHOLD_DP = 64.dp
