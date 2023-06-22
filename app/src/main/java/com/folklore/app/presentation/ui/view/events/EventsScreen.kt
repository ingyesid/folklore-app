package com.folklore.app.presentation.ui.view.events

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.folklore.app.R
import com.folklore.app.presentation.model.EventUiModel
import com.folklore.app.presentation.utils.EventsPreviewProvider
import com.folklore.app.presentation.utils.FolkloreIcons

@Composable
fun EventsScreen(
    viewModel: EventsViewModel = hiltViewModel(),
    onFilterOptionClick: () -> Unit,
    onSearchOptionClick: () -> Unit,
    onEventClick: (event: String) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsState().value
    EventsScreenContent(
        uiState = uiState,
        onFilterOptionClick = onFilterOptionClick,
        onSearchOptionClick = onSearchOptionClick,
        onEventClick = onEventClick,
    )
}

@Composable
fun EventsScreenContent(
    uiState: EventsUiState,
    onFilterOptionClick: () -> Unit,
    onSearchOptionClick: () -> Unit,
    onEventClick: (eventId: String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 4.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            TextButton(onClick = {
                onSearchOptionClick()
            }) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search Icon",
                    modifier = Modifier.size(24.dp),
                )
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    text = "Search",
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 1,
                )
            }
            TextButton(
                onClick = {
                    onFilterOptionClick()
                },
            ) {
                Icon(
                    imageVector = FolkloreIcons.Rounded.Sort(),
                    contentDescription = "Search Icon",
                    modifier = Modifier.size(24.dp),
                )
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    text = "Sort",
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1,
                )
            }
        }
        EventsGroup(
            header = stringResource(R.string.header_popular),
            horizontally = true,
            events = uiState.popularEvents,
            onClicked = { event ->
                onEventClick(event.id)
            },
        )
        EventsGroup(
            header = stringResource(R.string.header_near_you),
            events = uiState.nearEvents,
            onClicked = { event ->
                onEventClick(event.id)
            },
        )
    }
}

@Preview
@Composable
fun HomeScreenContentPreview(
    @PreviewParameter(EventsPreviewProvider::class) event: EventUiModel,
) {
    EventsScreenContent(
        uiState = EventsUiState(
            loading = false,
            popularEvents = listOf(event, event),
            nearEvents = listOf(event, event),
        ),
        onFilterOptionClick = { /*TODO*/ },
        onSearchOptionClick = { /*TODO*/ },
        onEventClick = {},
    )
}
