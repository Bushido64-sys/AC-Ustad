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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.lifecycle.viewmodel.compose.viewModel
import com.acustad.app.R
import com.acustad.app.model.CategoryId
import com.acustad.app.ui.browse.BrandsScreen
import com.acustad.app.ui.browse.BrandsViewModel
import com.acustad.app.ui.browse.CodesScreen
import com.acustad.app.ui.browse.CodesViewModel
import com.acustad.app.ui.browse.SeriesScreen
import com.acustad.app.ui.browse.SeriesViewModel
import com.acustad.app.ui.detail.CodeDetailScreen
import com.acustad.app.ui.detail.CodeDetailViewModel
import com.acustad.app.ui.home.HomeScreen

/**
 * The whole navigation graph: four screens, one linear path.
 *
 * ```
 * Home  ->  Brands(category)  ->  Series(brandId)  ->  Codes(seriesId, brandId)
 * ```
 *
 * The path is deliberately linear and shallow. A technician standing in front of a machine is
 * answering one question — what does this code mean — and every level of nesting is a tap they
 * make one-handed. No drawer, no bottom sheet, no tree. (DESIGN.md §4.1)
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
 */
object Routes {
    const val HOME = "home"
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

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        // Insets are applied once, here, rather than on every screen. targetSdk 35 means
        // Android 15 draws edge to edge whether the app likes it or not.
        NavHost(
            navController = nav,
            startDestination = Routes.HOME,
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(PaddingValues(horizontal = 16.dp, vertical = 8.dp)),
        ) {
            composable(Routes.HOME) {
                HomeScreen(onCategoryClick = { nav.navigate(Routes.brands(it)) })
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
                ScreenWithBar(
                    title = vm.seriesName.ifBlank { stringResource(R.string.heading_code) },
                    subtitle = vm.brandName.ifBlank { null },
                    onBack = { nav.popBackStack() },
                ) {
                    CodeDetailScreen()
                }
            }
        }
    }
}

/** An app bar above a screen's content. */
@Composable
private fun ScreenWithBar(
    title: String,
    onBack: () -> Unit,
    subtitle: String? = null,
    content: @Composable () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AcUstadAppBar(title = title, subtitle = subtitle, onBack = onBack)
        content()
    }
}
