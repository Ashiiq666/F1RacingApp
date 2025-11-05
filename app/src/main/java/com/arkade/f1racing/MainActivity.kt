package com.arkade.f1racing

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.arkade.f1racing.data.network.ApiService
import com.arkade.f1racing.data.repository.Repository
import com.arkade.f1racing.presentation.home.HomeViewModel
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

        apiService = ApiService()
        repository = Repository(apiService)
        homeViewModel = HomeViewModel(repository)

        setContent {
            F1RacingAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Navigation(
                        navController = navController,
                        homeViewModel = homeViewModel
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        apiService.close()
    }
}