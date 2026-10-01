package com.acustad.app.ui.detail

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.acustad.app.R
import com.acustad.app.ads.AdsManager
import com.acustad.app.ads.NativeAdCard
import com.acustad.app.ads.rememberNativeAdPool
import com.acustad.app.model.BilingualText
import com.acustad.app.model.CodeDetail
import com.acustad.app.model.ContentLanguage
import com.acustad.app.ui.common.BorderedPanel
import com.acustad.app.ui.common.EmptyState
import com.acustad.app.ui.common.Severity
import com.acustad.app.ui.common.RaisedPanel
import com.acustad.app.ui.common.SeverityChip
import com.acustad.app.ui.common.bilingualChipWord
import com.acustad.app.ui.common.indicatorVisuals
import com.acustad.app.ui.common.severityVisuals
import com.acustad.app.ui.theme.UstadType

/**
 * The screen the whole app exists for.
 *
 * Order is a product decision, not a layout accident: **severity and meaning first, then the
 * numbered fix steps, then the causes.** A technician standing in front of a unit wants the
 * action, not an essay. Causes are reference material and go last. (PHASE_4_CODE_DETAIL.md)
 *
 * Every block hides itself when the data is absent. There is no placeholder text anywhere on
 * this screen: `panasonic/panasonic-hf-self-diagnosis/H00` has no causes, and the causes block
 * is simply not there.
 */
@Composable
fun CodeDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: CodeDetailViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val notesExpanded by viewModel.notesExpanded.collectAsStateWithLifecycle()
    val wallPopup by viewModel.wallPopup.collectAsStateWithLifecycle()
    val needsNetPopup by viewModel.needsNetPopup.collectAsStateWithLifecycle()
    val activity = LocalContext.current as? Activity
    val nativePool = rememberNativeAdPool(size = 1)
    val detail = state.detail

    if (wallPopup) {
        SaveWallDialog(
            onWatch = {
                activity?.let {
                    AdsManager.showRewarded(
                        activity = it,
                        onEarned = viewModel::confirmStarAfterReward,
                        onUnavailable = viewModel::onRewardUnavailable,
                    )
                } ?: viewModel.onRewardUnavailable()
            },
            onDismiss = viewModel::dismissWall,
        )
    }
    if (needsNetPopup) {
        NeedsConnectionDialog(onDismiss = viewModel::dismissNeedsNet)
    }

    if (detail == null) {
        when {
            // The read threw. Say that, offer a retry, and never claim the code is missing.
            state.failed -> EmptyState(
                message = stringResource(R.string.error_reading_code),
                modifier = modifier.fillMaxSize(),
                actionLabel = stringResource(R.string.action_try_again),
                onAction = viewModel::reload,
            )
            state.isLoading -> LoadingPanel()
            // Genuinely absent from the database. This is a true statement about the data, and
            // it is now the only path that makes it.
            else -> EmptyState(
                message = stringResource(R.string.empty_code_not_found),
                modifier = modifier.fillMaxSize(),
            )
        }
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Headline(detail, state.language)

        val meaning = detail.meaning(state.language)
        if (meaning != null) {
            // Level 2 - raised. DESIGN.md §4.5 draws the meaning inside a raised block, and this
            // is the one piece of the screen that is genuinely a quotation: the manufacturer or
            // the manual's own words, not the app's. It is also the block a technician reads
            // first, so raising it puts the hierarchy in the order the design intended.
            RaisedPanel(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = meaning,
                    style = UstadType.body,
                    modifier = Modifier.padding(16.dp),
                )
            }
        }

        // HOW TO FIX before POSSIBLE CAUSES - the action, then the explanation.
        NumberedBlock(
            heading = stringResource(R.string.heading_how_to_fix),
            items = detail.solutions,
            language = state.language,
        )
        NumberedBlock(
            heading = stringResource(R.string.heading_possible_causes),
            items = detail.causes,
            language = state.language,
        )

        val notes = detail.notes(state.language)
        if (notes != null) {
            // Level 1, not raised. Notes are editorial rather than a quotation, and the design
            // groups them with the other collapsible blocks. Only the meaning and the source
            // line are level 2 on this screen.
            CollapsibleBlock(
                heading = stringResource(R.string.heading_notes),
                text = notes,
                expanded = notesExpanded,
                onToggle = viewModel::toggleNotes,
            )
        }

        SourceBlock(detail, state.language)

        // One native ad below the source line, never inside the answer. Badged
        // by construction (NativeAdCard), omitted while it has not filled.
        nativePool.adFor(0)?.let { NativeAdCard(ad = it) }
    }
}

/**
 * The save wall: the first three saves are free, this one needs a reward.
 *
 * A restyled `AlertDialog`, not a default one: 4dp corners, the app surface,
 * no tonal lift, verb buttons (DESIGN.md §6). Warm on purpose — "keeps AC
 * Ustad free" is the reason, stated once, and "Not now" is a real way out.
 */
@Composable
private fun SaveWallDialog(onWatch: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(4.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp,
        title = {
            Text(
                text = stringResource(R.string.save_wall_title),
                style = UstadType.section,
            )
        },
        text = {
            Text(
                text = stringResource(R.string.save_wall_text),
                style = UstadType.body,
            )
        },
        confirmButton = {
            TextButton(onClick = onWatch) {
                Text(
                    text = stringResource(R.string.save_wall_watch),
                    style = UstadType.label,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.save_wall_later),
                    style = UstadType.label,
                )
            }
        },
    )
}

/**
 * The wall's offline branch: no ad could be shown, so the save waits on a
 * connection. By decision (ADS.md) the save does not go through — and the
 * popup says exactly that in one line rather than spinning.
 */
@Composable
private fun NeedsConnectionDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(4.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp,
        title = {
            Text(
                text = stringResource(R.string.save_needs_net_title),
                style = UstadType.section,
            )
        },
        text = {
            Text(
                text = stringResource(R.string.save_needs_net_text),
                style = UstadType.body,
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.action_ok),
                    style = UstadType.label,
                )
            }
        },
    )
}

/**
 * The severity chip, the code, and the title.
 *
 * **The star used to be here and is not any more.** DESIGN.md §4.5 has always put it in the top
 * bar, beside the code, and `PHASE_8` §3 gives the reason: in the body it sits exactly where a
 * thumb is when the phone is held in one hand, which is the only way this app is ever held. It
 * now renders from `AcUstadNavHost` into the app bar's action slot, and the row below is just
 * the chip, so there is nothing here to scroll away from.
 *
 * The chip is first and stays first: RULE 8 says the word is the signal, and a severity a
 * technician has to scroll to find is a severity they will not find.
 */
@Composable
private fun Headline(detail: CodeDetail, language: ContentLanguage) {
    // A non-fault row (569 of 4,418) is an indicator or a parameter, not a breakdown.
    val visuals = if (detail.summary.isFault) {
        severityVisuals(Severity.from(detail.summary.severity), language)
    } else {
        indicatorVisuals(language)
    }
    val title = detail.summary.title(language)

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        SeverityChip(
            label = visuals.label,
            background = visuals.background,
            contentColor = visuals.content,
            border = visuals.border,
        )
        Text(text = detail.summary.code, style = UstadType.code)
        if (title != null) {
            Text(text = title, style = UstadType.title)
        }
    }
}

/**
 * A numbered list of fix steps or causes.
 *
 * The number matters: "try step 2" is what gets said down a phone, so the number sits in its own
 * 28dp bordered box on the left rather than inside the sentence's margin, where a wrapped line
 * would stop aligning with it. Steps are never truncated — a half-sentence instruction is worse
 * than scrolling. (PHASE_4_CODE_DETAIL.md §2)
 */
@Composable
private fun NumberedBlock(
    heading: String,
    items: List<BilingualText>,
    language: ContentLanguage,
) {
    if (items.isEmpty()) return
    val noun = if (items.size == 1) {
        stringResource(R.string.unit_step_one)
    } else {
        stringResource(R.string.unit_step_many, items.size)
    }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        HeadingWithCount(heading, noun)
        BorderedPanel(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items.forEach { item ->
                    NumberedItem(item = item, language = language)
                }
            }
        }
    }
}

@Composable
private fun NumberedItem(item: BilingualText, language: ContentLanguage) {
    val text = item.text(language) ?: return
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .semantics { contentDescription = "Step ${item.displayNumber}" },
            contentAlignment = Alignment.Center,
        ) {
            Text(text = item.displayNumber.toString(), style = UstadType.count)
        }
        Text(
            text = text,
            style = UstadType.body,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun HeadingWithCount(heading: String, count: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = heading, style = UstadType.section)
        Text(text = count, style = UstadType.count)
    }
}

/**
 * A block of text that is collapsed until asked for. Notes run to 725 characters (p90 202) and
 * are editorial rather than an instruction, so they are never in the way by default.
 */
@Composable
private fun CollapsibleBlock(
    heading: String,
    text: String,
    expanded: Boolean,
    onToggle: () -> Unit,
) {
    val label = if (expanded) {
        stringResource(R.string.action_hide_details)
    } else {
        stringResource(R.string.action_show_details)
    }
    val isLong = text.length > CodeDetailViewModel.NOTES_COLLAPSE_CHARS
    val shown = if (expanded || !isLong) text else text.take(CodeDetailViewModel.NOTES_COLLAPSE_CHARS).trimEnd()

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        HeadingWithCount(heading, "")
        BorderedPanel(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = shown, style = UstadType.body)
                if (isLong) {
                    TextButton(onClick = onToggle, modifier = Modifier.heightIn(min = 48.dp)) {
                        Text(text = label, style = UstadType.label)
                    }
                }
            }
        }
    }
}

/**
 * The source line.
 *
 * 26 of the 4,418 codes have no `source_url`. Those show the source as plain text with **no
 * link and no disabled button** — offering a control that cannot work is worse than offering
 * none. The confidence badge appears here and nowhere else.
 */
@Composable
private fun SourceBlock(detail: CodeDetail, language: ContentLanguage) {
    val type = detail.sourceType
    val title = detail.sourceTitle
    if (type == null && title == null) return
    val uriHandler = LocalUriHandler.current
    val link = detail.sourceUrl
    val openLabel = stringResource(R.string.action_open_source)

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        HeadingWithCount(stringResource(R.string.heading_source), "")
        // Level 2 - raised. DESIGN.md §4.5 and PHASE_11 §3.2 both put the source line on the
        // raised surface, and they are right: `source_type` and `source_title` are a citation,
        // which is a quotation, and showing provenance on the same footing as the app's own
        // copy would be quietly claiming it.
        RaisedPanel(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = listOfNotNull(type, title).joinToString(" · "),
                    style = UstadType.caption,
                )
                ConfidenceChip(detail.confidence, language)
                if (link != null) {
                    TextButton(
                        onClick = { runCatching { uriHandler.openUri(link) } },
                        modifier = Modifier
                            .heightIn(min = 48.dp)
                            .semantics { contentDescription = openLabel },
                    ) {
                        Text(text = stringResource(R.string.action_open_source), style = UstadType.label)
                    }
                }
            }
        }
    }
}

/**
 * The confidence badge: outline only, detail screen only, never in a list.
 *
 * It takes the language because **the word is content and must switch with it** (RULE 13). It
 * did not, for the badge's whole life, which meant the detail screen said HIGH while the
 * severity chip beside it said BAND KARO — a partial toggle, and the exact class of fault trap
 * 12 is about. The `confidence_*_ur` strings existed the whole time and were never referenced;
 * a declared-but-unused string is how this stayed invisible, because nothing failed.
 */
@Composable
private fun ConfidenceChip(confidence: String, language: ContentLanguage) {
    val label = when (confidence) {
        "high" -> bilingualChipWord(
            R.string.confidence_high, R.string.confidence_high_ur, language,
        )
        "medium" -> bilingualChipWord(
            R.string.confidence_medium, R.string.confidence_medium_ur, language,
        )
        else -> bilingualChipWord(
            R.string.confidence_low, R.string.confidence_low_ur, language,
        )
    }
    SeverityChip(
        label = label,
        background = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onSurface,
        border = androidx.compose.foundation.BorderStroke(
            2.dp,
            MaterialTheme.colorScheme.outlineVariant,
        ),
    )
}

@Composable
private fun LoadingPanel() {
    BorderedPanel(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.loading),
            style = UstadType.caption,
            modifier = Modifier.padding(16.dp),
        )
    }
}
