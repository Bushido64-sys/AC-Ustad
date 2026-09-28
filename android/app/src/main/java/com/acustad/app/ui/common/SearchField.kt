package com.acustad.app.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.acustad.app.R
import com.acustad.app.ui.theme.UstadType

/**
 * The search field.
 *
 * It is **always visible at the top of the list**, never behind an icon and never a full-screen
 * modal, because a technician one-handed in a dark room should not have to find a button to
 * start looking. The placeholder always names the scope — "Search brands", "Search models",
 * "Search codes in this model" — which is how the no-global-search rule is made legible rather
 * than surprising. (PHASE_5_SEARCH.md §4)
 */
@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderRes: Int,
    onClear: () -> Unit,
    modifier: Modifier = Modifier,
    /** Brand and model names are words, so they get sentence capitalisation; codes do not. */
    capitalizeWords: Boolean = true,
) {
    val keyboard = LocalSoftwareKeyboardController.current
    val clearLabel = stringResource(R.string.action_clear)
    val fieldLabel = stringResource(placeholderRes)

    Column(modifier = modifier.fillMaxWidth()) {
        BorderedPanel(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .heightIn(min = 56.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                androidx.compose.material3.OutlinedTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .weight(1f)
                        .defaultMinSize(minHeight = 48.dp)
                        .semantics { contentDescription = fieldLabel },
                    placeholder = {
                        Text(text = fieldLabel, style = UstadType.body)
                    },
                    singleLine = true,
                    textStyle = UstadType.body,
                    keyboardOptions = KeyboardOptions(
                        capitalization = if (capitalizeWords) {
                            KeyboardCapitalization.Words
                        } else {
                            KeyboardCapitalization.None
                        },
                        imeAction = ImeAction.Search,
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = { keyboard?.hide() },
                    ),
                )
                if (value.isNotEmpty()) {
                    androidx.compose.material3.TextButton(
                        onClick = {
                            onClear()
                            keyboard?.hide()
                        },
                        modifier = Modifier
                            .defaultMinSize(minHeight = 48.dp)
                            .semantics { contentDescription = clearLabel },
                    ) {
                        Text(
                            text = stringResource(R.string.action_clear),
                            style = UstadType.label,
                        )
                    }
                }
            }
        }
    }
}

/**
 * An empty state: one sentence, and a way forward.
 *
 * No illustration, no emoji, no "Try again" unless retrying is the actual fix. 8 brands and 65
 * model lines legitimately have no codes, so this is a screen a technician will meet often
 * and it must read as information, not as failure. (RULES.md RULE 17)
 */
@Composable
fun EmptyState(
    message: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(text = message, style = UstadType.body)
        if (actionLabel != null && onAction != null) {
            androidx.compose.material3.TextButton(
                onClick = onAction,
                modifier = Modifier.defaultMinSize(minHeight = 48.dp),
            ) {
                Text(text = actionLabel, style = UstadType.label)
            }
        }
    }
}

/**
 * The "show all" control. Brands are searchable, so the first 16 are rendered and the rest
 * revealed on request — this only reduces initial work, it never hides anything permanently.
 */
@Composable
fun ShowAllRow(
    shown: Int,
    total: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .semantics {
                contentDescription = "Show all $total, showing $shown"
            },
        shape = RoundedCornerShape(4.dp),
        color = androidx.compose.ui.graphics.Color.Transparent,
    ) {
        androidx.compose.material3.TextButton(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp),
        ) {
            Text(
                text = stringResource(R.string.action_show_all_count, total),
                style = UstadType.label,
            )
        }
    }
}
