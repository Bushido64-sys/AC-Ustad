package com.acustad.app.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.ui.platform.LocalContext
import com.acustad.app.R
import com.acustad.app.ads.AdsManager
import com.acustad.app.ads.BannerAd
import com.acustad.app.model.CategoryId
import com.acustad.app.ui.browse.BrandsScreen
import com.acustad.app.ui.browse.BrandsViewModel
import com.acustad.app.ui.browse.CodesScreen
import com.acustad.app.ui.browse.CodesViewModel
import com.acustad.app.ui.browse.SeriesScreen
import com.acustad.app.ui.browse.SeriesViewModel
import com.acustad.app.ui.common.StarToggle
import com.acustad.app.ui.detail.CodeDetailScreen
import com.acustad.app.ui.detail.CodeDetailViewModel
import com.acustad.app.ui.home.HomeScreen
import com.acustad.app.ui.saved.SavedScreen
import com.acustad.app.ui.saved.SavedState
import com.acustad.app.ui.saved.SavedViewModel
import com.acustad.app.ui.settings.SettingsScreen

/**
 * The whole navigation graph: one linear path and two top-level destinations.
 *
 * ```
 * Home    ->  Brands(category)  ->  Series(brandId)  ->  Codes(seriesId, brandId)  ->  Detail
 * Saved   ->  Detail
 * Settings
 * ```
 *
 * The browse path is deliberately linear and shallow. A technician standing in front of a
 * machine is answering one question — what does this code mean — and every level of nesting is a
 * tap they make one-handed. No drawer, no bottom sheet, no tree. (DESIGN.md §4.1)
 *
 * ### Routes carry slugs only
 *
 * A route segment must not contain `/` or parentheses, and real model names do:
 * "Splits — Inverter & Fixed-Speed (shared platform)". Percent-encoding them is possible but
 * fragile — `URLEncoder` turns a space into `+`, which the navigation library does not decode
 * back. So **no display name is ever put in a route**. `brandId` and `seriesId` are slugs
 * (`dawlance`, `inverter-split`), and each screen reads the names it needs from the database,
 * where they already are.
 *
 * ### seriesId is always accompanied by brandId
 *
 * `series.id` is unique only within a brand — 14 brands share the id `inverter-split` — so a
 * route carrying the id alone would be capable of showing the wrong codes. Passing both makes
 * that unrepresentable.
 *
 * ### The bottom bar appears on the three top-level destinations only
 *
 * It is hidden while drilling into brands, models, codes and a code's detail: those are a
 * continuous gesture downwards, and a persistent bar over a list a technician is scrolling
 * costs a row of content and invites a mis-tap mid-scroll. The bar is a way *out* to a
 * different top-level place, not a way *down*.
 */
object Routes {
    const val HOME = "home"
    const val SAVED = "saved"
    const val SETTINGS = "settings"
    const val BRANDS = "brands/{category}"
    const val SERIES = "series/{brandId}"
    const val CODES = "codes/{seriesId}/{brandId}"
    // The code is identified by codes.id, not by codes.uid: the uid is "brand/series/code" and
    // a route segment cannot contain slashes. The uid is still the identity used everywhere
    // else - here the integer key is simply the safe way to carry it between screens.
    const val DETAIL = "detail/{codeId}"

    private fun slug(value: String): String = value.ifEmpty { "-" }

    fun brands(category: CategoryId) =
        "brands/" + if (category == CategoryId.AC) "ac" else "inverter"

    fun series(brandId: String) = "series/${slug(brandId)}"

    fun codes(seriesId: String, brandId: String) = "codes/${slug(seriesId)}/${slug(brandId)}"

    fun detail(codeId: Long) = "detail/$codeId"
}

@Composable
fun AcUstadNavHost(modifier: Modifier = Modifier) {
    val nav = rememberNavController()
    val activity = LocalContext.current as? Activity

    // One instance for the whole app rather than one per screen, so the bottom bar's star and
    // the list are reading the same state. It also survives tab switches without a re-query.
    val savedViewModel: SavedViewModel = viewModel()
    val savedState by savedViewModel.state.collectAsStateWithLifecycle()

    val backStackEntry by nav.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route

    val tab = when (route) {
        Routes.SAVED -> NavTab.SAVED
        Routes.SETTINGS -> NavTab.SETTINGS
        else -> NavTab.BROWSE
    }
    val hasSaved = (savedState as? SavedState.Ready)?.items?.isNotEmpty() == true

    // Re-read on every arrival, not on every recomposition. A code is nearly always starred from
    // a detail screen, which holds its own view model and its own repository instance, so the
    // saved list is only correct once it has been read again after leaving that screen. Keying
    // on the tab instead would be wrong: the common round trip is Home -> code -> detail -> back,
    // and the tab is Browse at both ends of it, so nothing would ever re-read.
    //
    // The cost is one join of a two-column table against three indexed ones, over a list that is
    // six rows long in normal use.
    LaunchedEffect(backStackEntry) {
        if (backStackEntry != null) savedViewModel.reload()
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        // Insets are applied once, out here, rather than on every screen: targetSdk 35 means
        // Android 15 draws edge to edge whether the app likes it or not, and the bottom bar has
        // to clear the gesture bar while every other screen keeps its own padding.
        // `windowInsetsPadding` consumes what it applies, so a screen that also applies it
        // (HomeScreen) sees zero remaining insets and is not double-padded.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing),
        ) {
            NavHost(
                navController = nav,
                startDestination = Routes.HOME,
                modifier = Modifier
                    .weight(1f)
                    .padding(PaddingValues(horizontal = 16.dp, vertical = 8.dp)),
            ) {
                composable(Routes.HOME) {
                    // Leaving the app through the system back button: one
                    // interstitial (cap-gated inside), then the activity
                    // finishes. Nothing to save, nothing to confirm — the
                    // ad is the whole stop. (ADS.md)
                    BackHandler(enabled = activity != null) {
                        activity?.let {
                            AdsManager.showInterstitial(it) { it.finish() }
                        }
                    }
                    HomeScreen(onCategoryClick = { nav.navigate(Routes.brands(it)) })
                }

                composable(Routes.SAVED) {
                    ScreenWithBar(title = stringResource(R.string.nav_saved), onBack = null) {
                        SavedScreen(
                            // A saved row opens the same detail screen every other code opens.
                            onCodeClick = { item -> nav.navigate(Routes.detail(item.codeId)) },
                            viewModel = savedViewModel,
                        )
                    }
                }

                composable(Routes.SETTINGS) {
                    ScreenWithBar(title = stringResource(R.string.nav_settings), onBack = null) {
                        SettingsScreen()
                    }
                }

                composable(
                    route = Routes.BRANDS,
                    arguments = listOf(
                        navArgument(BrandsViewModel.ARG_CATEGORY) { type = NavType.StringType }
                    ),
                ) {
                    val vm: BrandsViewModel = viewModel()
                    val title = when (vm.category) {
                        CategoryId.AC -> stringResource(R.string.category_ac)
                        CategoryId.INVERTER -> stringResource(R.string.category_inverter)
                    }
                    ScreenWithBar(title = title, onBack = { nav.popBackStack() }) {
                        BrandsScreen(onBrandClick = { brandId ->
                            nav.navigate(Routes.series(brandId))
                        })
                    }
                }

                composable(
                    route = Routes.SERIES,
                    arguments = listOf(
                        navArgument(SeriesViewModel.ARG_BRAND_ID) { type = NavType.StringType }
                    ),
                ) {
                    val vm: SeriesViewModel = viewModel()
                    ScreenWithBar(title = vm.brandName, onBack = { nav.popBackStack() }) {
                        SeriesScreen(
                            onSeriesClick = { target ->
                                nav.navigate(Routes.codes(target.seriesId, target.brandId))
                            },
                        )
                    }
                }

                composable(
                    route = Routes.CODES,
                    arguments = listOf(
                        navArgument(CodesViewModel.ARG_SERIES_ID) { type = NavType.StringType },
                        navArgument(CodesViewModel.ARG_BRAND_ID) { type = NavType.StringType },
                    ),
                ) {
                    val vm: CodesViewModel = viewModel()
                    // Brand and model names are always English: a technician reads a model number in
                    // English by habit, and RULE 13 keeps them out of the UR toggle.
                    ScreenWithBar(
                        title = vm.seriesName,
                        subtitle = vm.brandName,
                        onBack = { nav.popBackStack() },
                    ) {
                        CodesScreen(onCodeClick = { code -> nav.navigate(Routes.detail(code.id)) })
                    }
                }

                composable(
                    route = Routes.DETAIL,
                    arguments = listOf(
                        navArgument(CodeDetailViewModel.ARG_CODE_ID) { type = NavType.LongType }
                    ),
                ) {
                    val vm: CodeDetailViewModel = viewModel()
                    // Collected here, not inside the screen, because the star lives in the app bar
                    // and the bar is rendered by this host. Same view model, same StateFlow: this
                    // is a second observer, not a second source of truth, so the body and the bar
                    // cannot disagree about whether the code is saved. (trap 12's cousin — two
                    // readers of one flow is fine; two writers is not.)
                    val detailState by vm.state.collectAsStateWithLifecycle()
                    ScreenWithBar(
                        title = vm.seriesName.ifBlank { stringResource(R.string.heading_code) },
                        subtitle = vm.brandName.ifBlank { null },
                        onBack = { nav.popBackStack() },
                        action = {
                            StarToggle(
                                filled = detailState.detail?.isFavourite == true,
                                onToggle = vm::toggleFavourite,
                            )
                        },
                    ) {
                        CodeDetailScreen()
                    }
                }
            }

            // One banner for the whole app, pinned between the content and the
            // bottom bar. It covers every screen identically — including the
            // detail screen, where it sits below the content and never inside
            // it — so there is one slot, one request, zero layout shift, and
            // no per-screen wiring to forget. (ADS.md)
            BannerAd()

            if (route == Routes.HOME || route == Routes.SAVED || route == Routes.SETTINGS) {
                AcUstadBottomNav(
                    selected = tab,
                    hasSaved = hasSaved,
                    onSelect = { target ->
                        when (target) {
                            // Popping to Home rather than navigating to it, so tapping Browse
                            // from four levels down unwinds in one step instead of stacking a
                            // second Home on top. Already on Home it is a no-op.
                            //
                            // Returning to the top level from deep in the browse path
                            // shows one interstitial first (cap-gated inside), then
                            // unwinds underneath the dismissed ad. (ADS.md)
                            NavTab.BROWSE -> {
                                if (route == Routes.HOME) {
                                    Unit
                                } else if (activity != null) {
                                    AdsManager.showInterstitial(activity) {
                                        nav.popBackStack(Routes.HOME, inclusive = false)
                                    }
                                } else {
                                    nav.popBackStack(Routes.HOME, inclusive = false)
                                }
                            }
                            NavTab.SAVED -> nav.navigate(Routes.SAVED) { launchSingleTop = true }
                            // Same rule as Saved. The bar is always visible here, so a tap while
                            // already on Settings must not stack a second copy of the screen —
                            // which would also put a second SettingsViewModel's worth of state on
                            // the back stack.
                            NavTab.SETTINGS -> nav.navigate(Routes.SETTINGS) { launchSingleTop = true }
                        }
                    },
                )
            }
        }
    }
}

/**
 * An app bar above a screen's content. A top-level screen has no back arrow.
 *
 * @param action passed straight through to [AcUstadAppBar], and used on exactly one screen: the
 *   code detail, where the star belongs next to the code rather than in the content body
 *   (DESIGN.md §4.5, `PHASE_8` §3). Keeping the plumbing here rather than inside
 *   [CodeDetailScreen] is what lets the detail screen's content be a plain scrollable column
 *   with no app bar in it at all — the bar is chrome, and chrome belongs to the host.
 */
@Composable
private fun ScreenWithBar(
    title: String,
    onBack: (() -> Unit)?,
    subtitle: String? = null,
    action: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AcUstadAppBar(title = title, subtitle = subtitle, onBack = onBack, action = action)
        content()
    }
}
