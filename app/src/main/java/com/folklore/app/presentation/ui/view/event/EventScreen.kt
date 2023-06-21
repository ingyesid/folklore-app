package com.folklore.app.presentation.ui.view.event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.folklore.app.presentation.model.EventDetailsUiModel

@Composable
fun EventScreen(
    viewModel: EventViewModel,
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    EventScreenContent(
        isLoading = uiState.loading,
        event = uiState.event,
        onBackClick = { onBackClick() },
        onToggleFavorite = {},
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventScreenContent(
    isLoading: Boolean,
    event: EventDetailsUiModel,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onToggleFavorite: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = event.title,
                )
            }, scrollBehavior = scrollBehavior, navigationIcon = {
                IconButton(onClick = {
                    onBackClick()
                }) {
                    Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Go Back")
                }
            })
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { contentPadding ->
        Column(
            modifier = Modifier.padding(contentPadding),
        ) {
            if (isLoading) {
                Box(Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            } else {
                AsyncImage(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .height(200.dp),
                    model = event.imageUrl,
                    contentDescription = event.title,
                    contentScale = ContentScale.Crop,
                )
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,

                    ) {
                        TextButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Outlined.Favorite,
                                contentDescription = "Likes Icon",
                                modifier = Modifier.size(24.dp),
                            )
                            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                            Text(
                                text = "${event.likes}",
                                style = MaterialTheme.typography.labelMedium,
                                maxLines = 1,
                            )
                        }
                        TextButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = "Going People Icon",
                                modifier = Modifier.size(24.dp),
                            )
                            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                            Text(
                                text = "${event.goingCount}",
                                style = MaterialTheme.typography.labelMedium,
                                maxLines = 1,
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = "Location Icon",
                        )
                        Text(
                            text = "${event.location}",
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                        )
                    }

                    Text(
                        text = event.startDate,
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1,
                    )
                    Text(
                        text = event.description,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 20,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun EventScreenContentPreview() {
    EventScreenContent(
        isLoading = false,
        event =
        EventDetailsUiModel(
            id = "xyz",
            title = "Google i/o extended",
            description = "lorem ipsum bla",
            imageUrl = "https://io.google/2021/assets/io_social_asset.jpg",
            goingCount = 20,
            likes = 30,
            location = "Corozal, Sucre",
            startDate = "May 29 , 2034",
        ),
        onBackClick = { },
        onToggleFavorite = {},
    )
}
