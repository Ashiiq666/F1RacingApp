package com.arkade.f1racing.presentation.navigations

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object RaceDetail : Screen("race_detail")
}