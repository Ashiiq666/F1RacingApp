package com.arkade.f1racing.presentation.home.components

sealed class HomeSliderItem {
    data class DriverInfo(
        val position: String,
        val wins: String,
        val points: String,
        val driverName: String
    ) : HomeSliderItem()
    
    data class Banner(
        val bannerRes: Int
    ) : HomeSliderItem()
}

