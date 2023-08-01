package com.folklore.app.presentation.ui.view.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.folklore.app.R
import com.folklore.app.presentation.model.FavoriteEventUiModel

@Composable
fun FavoriteEventItem(
    event: FavoriteEventUiModel,
    modifier: Modifier = Modifier,
    onClicked: (FavoriteEventUiModel) -> Unit,
) {
    OutlinedCard(
        modifier = Modifier.clickable {
            onClicked.invoke(event)
        },
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(90.dp)
        )
        {
            AsyncImage(
                modifier = Modifier
                    .background(Color.White)
                    .width(120.dp)
                    .fillMaxHeight(),
                model = event.imageUrl,
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = event.title,
                contentScale = ContentScale.Crop,
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            ) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = event.location,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Preview
@Composable
fun FavoriteEventItemPreview() {
    FavoriteEventItem(
        event = FavoriteEventUiModel(
            id = "xyz",
            title = "Google i/o extended",
            imageUrl = "https://io.google/2021/assets/io_social_asset.jpg",
            location = "Corozal, Sucre",
            startDate = "May 29 , 2034",
        ),
        onClicked = {}
    )
}
