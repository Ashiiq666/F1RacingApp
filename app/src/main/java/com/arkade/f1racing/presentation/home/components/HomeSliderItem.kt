package com.arkade.f1racing.presentation.home.components


import androidx.annotation.DrawableRes

sealed class HomeSliderItem {
    data class DriverInfo(
        val position: String,
        val wins: String,
        val points: String,
        val driverName: String,
        val teamName: String = "",
        val podiums: Int = 0,
        val poles: Int = 0
    ) : HomeSliderItem()

    data class Banner(
        @DrawableRes val bannerRes: Int
    ) : HomeSliderItem()
}
