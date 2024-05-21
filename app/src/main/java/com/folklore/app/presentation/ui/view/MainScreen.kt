package com.folklore.app.presentation.ui.view

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

    val tabs = remember { MainTabs.values() }
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true,
    )

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
                AppBar()
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
                        onEventClick = { eventId ->
                            onEventClick(eventId)
                        },
                    )
                }
                composable(MainDestinations.FAVORITES) {
                    FavoritesScreen(
                        viewModel = favoritesViewModel,
                        onFavoriteEventClick = { favoriteEvent ->
                            onEventClick(favoriteEvent.id)
                        },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                maxLines = 2,
            )
        },
        navigationIcon = {
            Icon(
                modifier = modifier.size(48.dp),
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = stringResource(id = R.string.app_logo_description),
            )
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
                modifier = Modifier.testTag(stringResource(tab.title)),
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
    const val FAVORITES = "favorites"
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
        MainDestinations.FAVORITES,
    ),
}

private enum class ModalSheetContent {
    FILTER,
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(onEventClick = {}, onSearchClick = {})
}
