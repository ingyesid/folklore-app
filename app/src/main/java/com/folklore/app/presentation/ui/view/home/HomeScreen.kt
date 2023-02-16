package com.folklore.app.presentation.ui.view.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.folklore.app.R
import com.folklore.app.presentation.ui.view.destinations.EventScreenDestination
import com.folklore.app.presentation.utils.Utilities
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(),
    navigator: DestinationsNavigator,
) {
    val homeUiState by homeViewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val collapsed = 16
    val expanded = 28

    val topAppBarTextSize =
        (collapsed + (expanded - collapsed) * (1 - scrollBehavior.state.collapsedFraction)).sp

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.home_screen_title),
                        fontSize = topAppBarTextSize,
                        maxLines = 2,
                    )
                },
                scrollBehavior = scrollBehavior,
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(
                    top = contentPadding.calculateTopPadding(),
                    start = 16.dp,
                    end = 16.dp,
                )
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search,
                ),
                value = homeUiState.searchBoxQuery,
                keyboardActions = KeyboardActions(onSearch = {
                    Log.d("Search action", "onClick")
                }),
                onValueChange = {
                    homeViewModel.searchEvents(it)
                },
                label = {
                    Text(
                        text = "Search and filter",
                        style = MaterialTheme.typography.labelMedium,
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.tune_24dp),
                        contentDescription = "Filter Icon",
                        modifier = Modifier.clickable {
                            Log.d("Filter Icon", "onClick")
                        },
                    )
                },
                shape = RoundedCornerShape(50.dp),
            )
            EventsGroup(
                header = "Popular",
                horizontally = true,
                events = Utilities.popularDemoEvents,
                onClicked = {
                    navigator.navigate(EventScreenDestination(event = it))
                },
            )
            EventsGroup(
                header = "Near you",
                events = Utilities.demoEvents,
                onClicked = {
                    navigator.navigate(EventScreenDestination(event = it))
                },
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenContentPreview() {
//    HomeScreenContent()
}
