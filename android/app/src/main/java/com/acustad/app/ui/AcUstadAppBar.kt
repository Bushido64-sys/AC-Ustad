package com.acustad.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.acustad.app.ui.theme.UstadType

/**
 * The app bar: a title, and at most one action.
 *
 * The back arrow is one of only five icons the app is allowed (RULES.md RULE 11) and it is
 * tinted with **ink, never the blue** — blue means "selected or primary", and a back arrow that
 * changes colour for decoration destroys that meaning. It is 48dp because it is used with
 * gloves.
 *
 * @param action **the** action slot, and there is exactly one. DESIGN.md §4.5 puts the star
 *   here on the code-detail screen, next to the code, and `PHASE_8` §3 says the same thing for
 *   a reason worth keeping: *"so a thumb never covers the code while tapping."* A star in the
 *   content body sits directly under the thumb when the phone is held in one hand, which is
 *   exactly the hand this app is read with.
 *
 *   The slot is `null` on every other screen, and it stays null rather than becoming a
 *   back-and-two-actions row. A screen with no action must not reserve space for one — that
 *   would push the title 48dp right on the screens that have nothing to do there, and a title
 *   that moves between screens is a title nobody can find.
 *
 *   The action is a composable slot rather than an icon + label pair on purpose: it is the only
 *   way a caller can put a *toggled* control (filled or outline) in it without the app bar
 *   knowing what a star is. `AcUstadAppBar` has no opinion about what the action does.
 */
@Composable
fun AcUstadAppBar(
    title: String,
    onBack: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    action: @Composable (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            if (onBack != null) {
                val backLabel = "Back"
                androidx.compose.material3.IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .defaultMinSize(minWidth = 48.dp, minHeight = 48.dp)
                        .semantics { contentDescription = backLabel },
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = UstadType.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    // The title itself is the label; the back button keeps its own, so nothing
                    // is merged over it.
                    modifier = Modifier.semantics { contentDescription = title },
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = UstadType.caption,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            if (action != null) {
                // The action is a sibling of the title column, not inside it, so it sits in the
                // row's own vertical centring and cannot be pushed around by a two-line title.
                action()
            }
        }
    }
}
