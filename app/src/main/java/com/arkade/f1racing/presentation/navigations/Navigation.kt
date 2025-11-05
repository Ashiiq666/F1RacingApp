package com.arkade.f1racing.presentation.navigations

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arkade.f1racing.presentation.calendar.EventScreen
import com.arkade.f1racing.presentation.details.DetailsScreen
import com.arkade.f1racing.presentation.home.HomeScreen
import com.arkade.f1racing.presentation.home.HomeViewModel
import com.arkade.f1racing.presentation.trophy.ResultScreen
import com.arkade.f1racing.presentation.web.WebScreen
import com.arkade.f1racing.presentation.profile.ProfileScreen
import androidx.compose.runtime.collectAsState
import androidx.navigation.Navigation

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

        composable(Screen.Event.route) {
            EventScreen(

            )
        }

        composable(Screen.Result.route) {
            ResultScreen(

            )
        }

        composable(Screen.Web.route) {
            WebScreen(

            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
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