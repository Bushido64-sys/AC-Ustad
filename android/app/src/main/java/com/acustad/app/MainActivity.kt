package com.acustad.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.acustad.app.repo.KbRepository
import com.acustad.app.ui.AcUstadNavHost
import com.acustad.app.ui.theme.AcUstadTheme

/**
 * The single activity. There is no AppCompat, no fragment and no multi-backstack: a five-screen
 * app does not need one. (DEPENDENCIES.md §2)
 *
 * ### Why the theme is read here and not inside a screen
 *
 * The theme wraps the **whole** navigation graph, so it has to be resolved before any screen is
 * composed. Reading it from the shared repository rather than from a `remember` inside Settings
 * means three things come out right:
 *
 *  - the stored theme is correct on the very first frame, so the app never flashes light before
 *    going dark — the same argument the repository's KDoc makes for the content language;
 *  - one value reaches every screen at once, so a theme change is not a partial re-theme;
 *  - it survives a configuration change, because the value lives in the process singleton and
 *    not in a composition that gets thrown away and rebuilt.
 *
 * `KbRepository.get` is the shared instance, so this is the same object every view model uses
 * and no second copy of the preference flow exists. (trap 12)
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repo = KbRepository.get(applicationContext)
        setContent {
            val theme by repo.theme.collectAsStateWithLifecycle()
            AcUstadTheme(dark = theme.isDark(isSystemInDarkTheme())) {
                AcUstadNavHost()
            }
        }
    }
}
