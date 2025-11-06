package com.arkade.f1racing.presentation.navigations

import com.arkade.f1racing.R

sealed class Screen(
    val route: String,
    val title: String? = null,
    val icon: Int? = null,
    val iconActive: Int? = null
) {
    object Splash : Screen(
        route = "splash",
        title = null,
        icon = null,
        iconActive = null
    )
    
    object Home : Screen(
        route = "home",
        icon = R.drawable.ic_home_inactive,
        iconActive = R.drawable.ic_home
    )
    
    object Event : Screen(
        route = "event",
        icon = R.drawable.calender_inactive,
        iconActive = R.drawable.calender_active
    )
    
    object Result : Screen(
        route = "result",
        icon = R.drawable.trophy_inactive,
        iconActive = R.drawable.trophy_active
    )
    
    object Web : Screen(
        route = "web",
        icon = R.drawable.ic_web_inactive,
        iconActive = R.drawable.ic_web_active
    )
    
    object Profile : Screen(
        route = "profile",
        icon = R.drawable.ic_profile_inactive,
        iconActive = R.drawable.ic_profile_active
    )
    
    object RaceDetail : Screen(
        route = "race_detail",
        title = null,
        icon = null,
        iconActive = null
    )
}