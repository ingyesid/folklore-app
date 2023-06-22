package com.folklore.app.presentation.ui.view.events

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.folklore.app.R
import com.folklore.app.presentation.model.EventUiModel
import com.folklore.app.presentation.utils.EventsPreviewProvider

@Composable
fun PopularEventItem(
    event: EventUiModel,
    modifier: Modifier = Modifier,
    onClicked: (EventUiModel) -> Unit,
) {
    ElevatedCard(
        modifier = Modifier.width(160.dp).clickable {
            onClicked.invoke(event)
        },
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            AsyncImage(
                modifier = Modifier.background(Color.White).fillMaxWidth().height(80.dp),
                model = event.imageUrl,
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = event.title,
                contentScale = ContentScale.Crop,
            )

            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = event.title,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = event.location,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = "Likes Icon",
                        modifier = Modifier.size(10.dp),
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text(
                        text = "${event.likes}",
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1,
                    )
                }
                TextButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "Going People Icon",
                        modifier = Modifier.size(10.dp),
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text(
                        text = "${event.goingCount}",
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PopularEventItemPreview(
    @PreviewParameter(EventsPreviewProvider::class) event: EventUiModel,
) {
    PopularEventItem(
        event = event,
        onClicked = { },
    )
}
