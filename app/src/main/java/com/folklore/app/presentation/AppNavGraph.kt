/*
 * Copyright 2023 Chris Anderson.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.folklore.app.presentation

import android.app.Activity
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
                onEventClick = {},
                onSearchClick = {},
            )
        }
        composable(
            AppDestinations.MOVIE_DETAIL,
            arguments = listOf(navArgument("eventId") { type = NavType.LongType }),
        ) {
            val viewModel = hiltViewModel<EventViewModel>()
            EventScreen(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }
        composable(AppDestinations.SEARCH) {}
    }
}

object AppDestinations {
    const val MAIN = "home"
    const val MOVIE_DETAIL = "event/{eventId}"
    const val SEARCH = "search"
}
