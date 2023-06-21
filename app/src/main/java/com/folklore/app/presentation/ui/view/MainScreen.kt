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

package com.folklore.app.presentation.ui.view

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.folklore.app.R
import com.folklore.app.presentation.ui.view.events.EventsScreen
import com.folklore.app.presentation.ui.view.events.EventsViewModel
import com.folklore.app.presentation.ui.view.favorites.FavoritesScreen
import com.folklore.app.presentation.ui.view.favorites.FavoritesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onEventClick: (String) -> Unit,
    onSearchClick: () -> Unit,
    homeViewModel: EventsViewModel = hiltViewModel(),
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
) {
    val homeViewState by homeViewModel.uiState.collectAsState()
    val favoritesUiState by favoritesViewModel.uiState.collectAsState()

    val tabs = remember { MainTabs.values() }
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentTabDestination = navBackStackEntry?.destination
    val isFavoritesListTab = currentTabDestination?.route == MainDestinations.WATCHLIST
    var modalSheetContent = remember { ModalSheetContent.FILTER }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetContent = {
            if (modalSheetState.isVisible) {
                // show sort and filter modal content
            }
        },
    ) {
        Scaffold(
            topBar = {
                AppBar(
                    onSearchClick = onSearchClick,
                )
            },
            bottomBar = {
                BottomBar(navController, tabs)
            },
        ) { innerPaddingModifier ->
            NavHost(
                navController = navController,
                startDestination = MainDestinations.HOME,
                modifier = Modifier.padding(innerPaddingModifier),
            ) {
                composable(MainDestinations.HOME) {
                    EventsScreen(
                        viewModel = homeViewModel,
                        onFilterOptionClick = {
                            coroutineScope.launch {
                                modalSheetContent = ModalSheetContent.FILTER
                                modalSheetState.show()
                            }
                        },
                        onSearchOptionClick = { onSearchClick() },
                        onEventClick = {},
                    )
                }
                composable(MainDestinations.WATCHLIST) {
                    FavoritesScreen(
                        viewModel = favoritesViewModel,
                        onFavoriteEventClick = { },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier.testTag("mainAppBar"),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        navigationIcon = {
            Icon(
                painter = painterResource(R.drawable.tune_24dp),
                contentDescription = stringResource(id = R.string.app_logo_description),
            )
        },
        actions = {
            IconButton(
                onClick = onSearchClick,
                modifier = Modifier.testTag("searchButton"),
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search_icon_description),
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
    )
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    tabs: Array<MainTabs>,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar {
        tabs.forEach { tab ->
            NavigationBarItem(
                icon = {
                    Icon(
                        tab.icon,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(tab.title)) },
                selected = currentDestination?.hierarchy?.any { it.route == tab.route } == true,
                onClick = {
                    navController.navigate(tab.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
            )
        }
    }
}

private object MainDestinations {
    const val HOME = "home"
    const val WATCHLIST = "favorites"
}

private enum class MainTabs(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String,
) {
    HOME(R.string.tab_home, Icons.Default.Home, MainDestinations.HOME),
    FAVORITES(
        R.string.tab_favorites,
        Icons.Default.Favorite,
        MainDestinations.WATCHLIST,
    ),
}

private enum class ModalSheetContent {
    FILTER,
}
