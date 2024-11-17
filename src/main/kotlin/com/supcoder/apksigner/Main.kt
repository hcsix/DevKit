package com.supcoder.apksigner

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.supcoder.apksigner.ui.AnotherScreen
import com.supcoder.apksigner.ui.HomeScreen
import com.supcoder.apksigner.ui.LanguageScreen
import com.supcoder.apksigner.ui.Router

@Composable
@Preview
fun App() {

    var currentLanguage by remember { mutableStateOf("") }

    var currentScreen: Router by remember { mutableStateOf(Router.HomeScreen) }


    val changeScreen = { screen: Router -> currentScreen = screen}


    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize()) {

            AnimatedVisibility(
                visible = currentScreen == Router.HomeScreen,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                HomeScreen(changeScreen )
            }
            AnimatedVisibility(
                visible = currentScreen == Router.LanguageScreen,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                LanguageScreen(changeScreen)
            }
            AnimatedVisibility(
                visible = currentScreen == Router.OtherScreen,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                AnotherScreen(changeScreen)
            }
        }
    }
}



fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
