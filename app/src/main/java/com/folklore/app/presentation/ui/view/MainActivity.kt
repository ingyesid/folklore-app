package com.folklore.app.presentation.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.folklore.app.presentation.ui.theme.FolkloreTheme
import com.folklore.app.presentation.ui.view.home.HomeScreen
import com.folklore.app.presentation.ui.view.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FolkloreTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val viewModel: HomeViewModel = hiltViewModel()
                    val uiState = viewModel.uiState.collectAsState()
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            HomeScreen(
                                homeUiState = uiState.value,
                                onEventClick = {
                                    navController.navigate("event")
                                },
                                onSearchTextChanged = {},
                                onSearchOptionClick = {},
                            )
                        }
                        composable(
                            route = "event/{event}",
                            arguments = listOf(
                                navArgument("event") { type = NavType.StringType },
                            ),
                        ) { navBackStackEntry ->
                        }
                    }
                }
            }
        }
    }
}
