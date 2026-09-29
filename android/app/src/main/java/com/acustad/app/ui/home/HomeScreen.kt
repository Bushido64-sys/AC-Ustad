package com.acustad.app.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.acustad.app.R
import com.acustad.app.model.CategoryId
import com.acustad.app.model.ContentLanguage
import com.acustad.app.ui.common.BorderedPanel
import com.acustad.app.ui.common.ContentLanguageToggle
import com.acustad.app.ui.common.PanelColumn
import com.acustad.app.ui.theme.UstadType

/**
 * The home screen: two choices, or get out of the way.
 *
 * Deliberately absent (RULES.md RULE 10): no hero image, no gradient, no marketing copy,
 * no "Get Started", no search box. A global code search is banned here — code search is
 * scoped to the model line the user is standing in front of (RULE 3).
 *
 * The counts come from the database at runtime, never hard-coded.
 */
@Composable
fun HomeScreen(
    onCategoryClick: (CategoryId) -> Unit = {},
    viewModel: HomeViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val language by viewModel.language.collectAsStateWithLifecycle()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                // Insets are handled explicitly: targetSdk 35 means Android 15 draws edge to
                // edge whether we like it or not.
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(PaddingValues(16.dp)),
        ) {
            when (val s = state) {
                is HomeState.Loading -> LoadingBlock()
                is HomeState.Failed -> FailedBlock(s.message, viewModel::load)
                is HomeState.Ready -> ReadyContent(s, language, onCategoryClick, viewModel::setLanguage)
            }
        }
    }
}

@Composable
private fun ReadyContent(
    state: HomeState.Ready,
    language: ContentLanguage,
    onCategoryClick: (CategoryId) -> Unit,
    onLanguage: (ContentLanguage) -> Unit,
) {
    val counts = state.counts
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = UstadType.title,
        )
        Text(
            text = stringResource(R.string.home_subtitle, state.kbVersion),
            style = UstadType.caption,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        CategoryPanel(
            title = stringResource(R.string.category_ac),
            brands = counts.acBrands,
            codes = counts.acCodes,
            onClick = { onCategoryClick(CategoryId.AC) },
        )
        CategoryPanel(
            title = stringResource(R.string.category_inverter),
            brands = counts.inverterBrands,
            codes = counts.inverterCodes,
            onClick = { onCategoryClick(CategoryId.INVERTER) },
        )

        // Below the two choices, not above them: the technician's question is "what does this
        // code mean", and the language is a preference, not a decision. Home is the right home
        // for it because the bottom bar's Browse tab puts this screen one tap away from
        // anywhere in the app. The Settings screen, in Phase 8's next step, will own it properly
        // alongside the theme and the data version.
        ContentLanguageToggle(selected = language, onSelect = onLanguage)
    }
}

@Composable
private fun CategoryPanel(title: String, brands: Int, codes: Int, onClick: () -> Unit) {
    val description = "$title, $brands brands, $codes codes"
    BorderedPanel(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
    ) {
        PanelColumn {
            Text(text = title, style = UstadType.title)
            Text(
                text = "$brands brands · $codes codes",
                style = UstadType.caption,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun LoadingBlock() {
    // A bordered panel with the app name, not a centred spinner: a shimmer is the generic
    // look this design avoids (DESIGN.md §5).
    BorderedPanel(modifier = Modifier.fillMaxWidth()) {
        PanelColumn {
            Text(
                text = stringResource(R.string.app_name),
                style = UstadType.title,
            )
            Text(
                text = stringResource(R.string.loading),
                style = UstadType.caption,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun FailedBlock(message: String, onRetry: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(R.string.error_opening_database),
            style = UstadType.listRow,
        )
        Text(
            text = message,
            style = UstadType.caption,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        TextButton(
            onClick = onRetry,
            modifier = Modifier
                .align(Alignment.Start)
                .semantics { contentDescription = "Try again" },
        ) {
            Text(text = stringResource(R.string.action_try_again), style = UstadType.label)
        }
    }
}
