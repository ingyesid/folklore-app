package com.folklore.app.presentation.ui.view.events

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.folklore.app.R
import com.folklore.app.presentation.model.EventUiModel

@Composable
fun EventsScreen(
    viewModel: EventsViewModel = hiltViewModel(),
    onFilterOptionClick: () -> Unit,
    onSearchOptionClick: () -> Unit,
    onEventClick: (event: EventUiModel) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsState().value
    EventsScreenContent(
        uiState = uiState,
        onFilterOptionClick = onFilterOptionClick,
        onSearchOptionClick = onSearchOptionClick,
        onEventClick = onEventClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsScreenContent(
    uiState: EventsUiState,
    onFilterOptionClick: () -> Unit,
    onSearchOptionClick: () -> Unit,
    onEventClick: (event: EventUiModel) -> Unit,
) {
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
                modifier = Modifier.fillMaxWidth().clickable {
                    onSearchOptionClick()
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search,
                ),
                value = "",
                onValueChange = {},
                label = {
                    Text(
                        text = stringResource(R.string.label_search_and_filter),
                        style = MaterialTheme.typography.labelMedium,
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.tune_24dp),
                        contentDescription = "Filter Icon",
                        modifier = Modifier.clickable {
                            onFilterOptionClick()
                        },
                    )
                },
                shape = RoundedCornerShape(50.dp),
            )
            EventsGroup(
                header = stringResource(R.string.header_popular),
                horizontally = true,
                events = uiState.popularEvents,
                onClicked = { event ->
                    onEventClick(event)
                },
            )
            EventsGroup(
                header = stringResource(R.string.header_near_you),
                events = uiState.nearEvents,
                onClicked = { event ->
                    onEventClick(event)
                },
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenContentPreview() {
    EventsScreenContent(
        uiState = EventsUiState(
            loading = false,
            popularEvents = listOf(
                EventUiModel(
                    id = "xyz",
                    title = "Google i/o extended",
                    shortDescription = "lorem ipsum bla",
                    imageUrl = "https://io.google/2021/assets/io_social_asset.jpg",
                    goingCount = 20,
                    likes = 30,
                    location = "Corozal, Sucre",
                    startDate = "May 29 , 2034",
                ),
            ),
        ),
        onFilterOptionClick = { /*TODO*/ },
        onSearchOptionClick = { /*TODO*/ },
        onEventClick = {},
    )
}
