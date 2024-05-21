package com.folklore.app.presentation.ui.view.events

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.folklore.app.presentation.model.EventUiModel
import com.folklore.app.presentation.utils.EventsPreviewProvider

@Composable
fun EventsGroup(
    modifier: Modifier = Modifier,
    header: String,
    horizontally: Boolean = false,
    events: List<EventUiModel>,
    onClicked: (EventUiModel) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (events.isNotEmpty())
            Text(
                text = header,
                style = MaterialTheme.typography.titleMedium,
            )
    }
    if (horizontally) {
        LazyRow(
            modifier = modifier.testTag(header),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(end = 8.dp),
        ) {
            items(events) { event ->
                PopularEventItem(event = event, onClicked = onClicked)
            }
        }
    } else {
        LazyColumn(
            modifier = modifier.testTag(header),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
        ) {
            items(events) { event ->
                EventItem(event = event, onClicked = onClicked)
            }
        }
    }

}

@Preview
@Composable
fun EventsGroupPreview(
    @PreviewParameter(EventsPreviewProvider::class) event: EventUiModel,
) {
    EventsGroup(
        header = "Popular",
        events = listOf(event),
        onClicked = {},
    )
}
