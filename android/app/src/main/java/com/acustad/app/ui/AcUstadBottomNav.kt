package com.acustad.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.acustad.app.R
import com.acustad.app.ui.common.StarIcon
import com.acustad.app.ui.common.hardShadow
import com.acustad.app.ui.theme.UstadType

/**
 * The three top-level destinations. A closed set rather than a route string, so a tab cannot be
 * added without the navigation host also learning to open it.
 */
enum class NavTab { BROWSE, SAVED, SETTINGS }

/**
 * The bottom bar. Three items: Browse, Saved, Settings.
 *
 * It carries the design's one structural blue and nothing else:
 *
 *  - 56dp tall, label always visible, a 2dp hairline across the top, canvas behind it. Not a
 *    floating bar, and never a pill.
 *  - **Selected:** the label and a 4dp indicator in `#1668A8`, plus the hard `3dp 3dp 0`
 *    shadow. The selected state is one of only two places a shadow is allowed at all
 *    (RULES.md RULE 9), which is most of what stops the bar looking like a template.
 *  - **Unselected:** ink, no shadow, star outline rather than a fill.
 *
 * The star's *fill* answers "do I have anything saved"; its *tint* answers "am I on this tab".
 * Those are two different questions, and a technician glancing at the bar should be able to
 * answer the first one without opening the tab.
 *
 * **Only Saved has an icon, and that is the budget.** RULE 11 allows five icons in the whole app
 * and four are already spoken for. Browse and Settings are text-only and keep the icon slot empty
 * so their labels sit on one baseline with Saved's — a label that jumps when the tab changes is
 * worse than a label with a little air above it. The alternative, a gear, would be a sixth icon
 * for a control that opens nothing more interesting than a language switch.
 *
 * Settings arrived with its screen rather than before it: a tab that opens nothing is worse than
 * a tab that does not exist yet. (PHASE_6_FAVOURITES.md §5)
 */
@Composable
fun AcUstadBottomNav(
    selected: NavTab,
    hasSaved: Boolean,
    onSelect: (NavTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        // The top line is `outline` (ink) at 2dp, and **not** the 2dp `hairline` DESIGN.md §1
        // asks for here. Measured: hairline on canvas is 1.41:1 in light and 1.47:1 in dark,
        // against a 3.0 non-text bar — a line that does not exist on a cheap LCD in sunlight,
        // and the one element of this bar carrying structural meaning.
        //
        // PHASE_8 §6 ("no thin text-coloured separators for meaningful grouping — use a 2dp ink
        // border or spacing") wins over DESIGN.md §1 here, because PHASE_8 is the document
        // written for a screen in sun. `hairline` survives in exactly one place, the `info`
        // chip's border, where DESIGN.md §3 already accepts 1.42:1 because the *word* is the
        // signal. (PHASE_11 §2.2, trap 20)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(HAIRLINE_DP)
                .background(MaterialTheme.colorScheme.outline),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(NAV_BAR_HEIGHT_DP)
                .background(MaterialTheme.colorScheme.background),
        ) {
            NavItem(
                label = stringResource(R.string.nav_browse),
                selected = selected == NavTab.BROWSE,
                onClick = { onSelect(NavTab.BROWSE) },
            )
            NavItem(
                label = stringResource(R.string.nav_saved),
                selected = selected == NavTab.SAVED,
                onClick = { onSelect(NavTab.SAVED) },
                icon = {
                    StarIcon(
                        filled = hasSaved,
                        tint = if (selected == NavTab.SAVED) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        },
                        size = ICON_SIZE_DP,
                    )
                },
            )
            // No icon, deliberately — see the KDoc. The slot is reserved by NavItem so this
            // label sits on the same baseline as the other two.
            NavItem(
                label = stringResource(R.string.nav_settings),
                selected = selected == NavTab.SETTINGS,
                onClick = { onSelect(NavTab.SETTINGS) },
            )
        }
    }
}

/**
 * One tab.
 *
 * A **`RowScope` extension, and that is not decoration.** `Modifier.weight` is declared inside
 * `RowScope`, not on `Modifier`, so it only resolves where a `RowScope` receiver is in scope.
 * Writing this as a plain top-level composable that takes a `Modifier` and calls
 * `modifier.weight(1f)` inside its own body does not compile: the caller's `Row` receiver is not
 * inherited by a normal function call. The other screens get away with `weight` because theirs
 * sits inside an inner `Row { }` content lambda, which does carry the receiver.
 */
@Composable
private fun RowScope.NavItem(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
) {
    val primary = MaterialTheme.colorScheme.primary
    Column(
        modifier = modifier
            .weight(1f)
            .height(NAV_BAR_HEIGHT_DP)
            // `selectable` rather than `clickable`, so a screen reader announces this tab as
            // selected and the bar is reachable with a switch-access gesture.
            .selectable(selected = selected, role = Role.Tab, onClick = onClick)
            .semantics { contentDescription = label },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier.height(ICON_SIZE_DP),
            contentAlignment = Alignment.Center,
        ) {
            icon?.invoke()
        }
        Text(
            text = label,
            style = UstadType.caption,
            color = if (selected) primary else MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
        )
        Box(modifier = Modifier.height(INDICATOR_GAP_DP))
        if (selected) {
            Box(
                modifier = Modifier
                    .width(INDICATOR_WIDTH_DP)
                    .height(INDICATOR_HEIGHT_DP)
                    .hardShadow()
                    .background(primary),
            )
        } else {
            // Reserved, not drawn. The slot is here so the labels do not shift between tabs,
            // and an undrawn indicator is also an unshadowed one.
            Box(
                modifier = Modifier
                    .width(INDICATOR_WIDTH_DP)
                    .height(INDICATOR_HEIGHT_DP),
            )
        }
    }
}

/** 56dp: the same minimum as a list row, because a nav item is used with gloves too. */
private val NAV_BAR_HEIGHT_DP = 56.dp

/** 2dp ink border, as on every card, row and chip. The app's structural line weight. */
private val HAIRLINE_DP = 2.dp

/** 4dp indicator, not a pill. (design_tokens.xml: stroke_selected) */
private val INDICATOR_HEIGHT_DP = 4.dp
private val INDICATOR_WIDTH_DP = 24.dp
private val INDICATOR_GAP_DP = 2.dp

private val ICON_SIZE_DP = 20.dp
