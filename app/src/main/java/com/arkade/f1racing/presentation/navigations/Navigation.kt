package com.arkade.f1racing.presentation.navigations

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arkade.f1racing.presentation.details.DetailsScreen
import com.arkade.f1racing.presentation.home.HomeScreen
import com.arkade.f1racing.presentation.home.HomeViewModel
import androidx.compose.runtime.collectAsState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(
    navController: NavHostController,
    homeViewModel: HomeViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = homeViewModel,
                onRaceCardClick = {
                    navController.navigate(Screen.RaceDetail.route)
                }
            )
        }

        composable(Screen.RaceDetail.route) {
            val race = homeViewModel.uiState.collectAsState().value.upcomingRace
            if (race != null) {
                DetailsScreen(
                    race = race,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}