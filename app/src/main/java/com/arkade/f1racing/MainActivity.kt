package com.arkade.f1racing

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.arkade.f1racing.data.network.ApiService
import com.arkade.f1racing.data.repository.Repository
import com.arkade.f1racing.presentation.home.HomeViewModel
import com.arkade.f1racing.presentation.navigations.BottomNavigationBar
import com.arkade.f1racing.presentation.navigations.Navigation
import com.arkade.f1racing.ui.theme.F1RacingAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var apiService: ApiService
    private lateinit var repository: Repository
    private lateinit var homeViewModel: HomeViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize dependencies
        apiService = ApiService()
        repository = Repository(apiService)
        homeViewModel = HomeViewModel(repository)

        setContent {
            F1RacingAppTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route ?: "home"

                // Define routes where bottom nav should be shown
                val bottomNavRoutes = listOf("home", "event", "result", "web", "profile")
                val showBottomBar = currentRoute in bottomNavRoutes

                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            BottomNavigationBar(
                                selectedRoute = currentRoute,
                                onItemSelected = { route ->
                                    if (route != currentRoute) {
                                        navController.navigate(route) {
                                            // Pop up to the start destination to avoid building up a large stack
                                            popUpTo("home") {
                                                saveState = true
                                            }
                                            // Avoid multiple copies of the same destination
                                            launchSingleTop = true
                                            // Restore state when reselecting a previously selected item
                                            restoreState = true
                                        }
                                    }
                                }
                            )
                        }
                    }
                ) { paddingValues ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Navigation(
                            navController = navController,
                            homeViewModel = homeViewModel
                        )
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        apiService.close()
    }
}