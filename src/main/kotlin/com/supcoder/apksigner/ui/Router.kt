package com.supcoder.apksigner.ui

sealed class Router(val route: String) {
    object HomeScreen : Router("home")
    object LanguageScreen : Router("language")
    object OtherScreen : Router("other")
}