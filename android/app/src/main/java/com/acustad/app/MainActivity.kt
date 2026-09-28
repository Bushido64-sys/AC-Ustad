package com.acustad.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.acustad.app.ui.home.HomeScreen
import com.acustad.app.ui.theme.AcUstadTheme

/**
 * The single activity. There is no AppCompat, no navigation graph and no fragment:
 * a 7-screen app does not need one yet (DEPENDENCIES.md §2).
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AcUstadTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    HomeScreen()
                }
            }
        }
    }
}
