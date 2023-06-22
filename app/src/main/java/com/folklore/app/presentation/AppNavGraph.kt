package com.folklore.app.presentation

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.folklore.app.presentation.ui.view.MainScreen
import com.folklore.app.presentation.ui.view.event.EventScreen
import com.folklore.app.presentation.ui.view.event.EventViewModel

@Composable
fun AppNavGraph(
    activity: Activity,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.MAIN,
        modifier = modifier,
    ) {
        composable(AppDestinations.MAIN) {
            MainScreen(
                onEventClick = { eventId ->
                    navController.navigate("event/$eventId")
                },
                onSearchClick = {},
            )
        }
        composable(
            AppDestinations.MOVIE_DETAIL,
            arguments = listOf(navArgument("eventId") { type = NavType.StringType }),
        ) {
            val viewModel = hiltViewModel<EventViewModel>()
            EventScreen(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }
        composable(AppDestinations.SEARCH) {
            //show search screen
        }
    }
}

object AppDestinations {
    const val MAIN = "home"
    const val MOVIE_DETAIL = "event/{eventId}"
    const val SEARCH = "search"
}
