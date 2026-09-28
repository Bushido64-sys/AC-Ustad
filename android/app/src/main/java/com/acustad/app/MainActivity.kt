package com.acustad.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.acustad.app.ui.AcUstadNavHost
import com.acustad.app.ui.theme.AcUstadTheme

/**
 * The single activity. There is no AppCompat, no fragment and no multi-backstack: a four-screen
 * app does not need one. (DEPENDENCIES.md §2)
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AcUstadTheme {
                AcUstadNavHost()
            }
        }
    }
}
