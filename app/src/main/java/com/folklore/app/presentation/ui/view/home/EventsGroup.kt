package com.folklore.app.presentation.ui.view.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.folklore.app.presentation.model.EventModel
import com.folklore.app.presentation.utils.Utilities

@Composable
fun EventsGroup(
    header: String,
    horizontally: Boolean = false,
    events: List<EventModel>,
    modifier: Modifier = Modifier,
    onClicked: (EventModel) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = header,
            style = MaterialTheme.typography.titleMedium
        )
        if (horizontally) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(end = 8.dp)
            ) {
                items(events) { event ->
                    PopularEventItem(event = event, onClicked = onClicked)
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(events) { event ->
                    EventItem(event = event, onClicked = onClicked)
                }
            }
        }
    }
}

@Preview
@Composable
fun PopularEventsGroupPreview() {
    EventsGroup(
        header = "Popular",
        horizontally = true,
        events = Utilities.demoEvents,
        onClicked = {
        }
    )
}

@Preview
@Composable
fun EventsGroupPreview() {
    EventsGroup(
        header = "Near you",
        horizontally = false,
        events = Utilities.demoEvents,
        onClicked = {
        }
    )
}
