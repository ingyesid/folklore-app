package com.folklore.app.presentation.ui.view.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.folklore.app.R
import com.folklore.app.presentation.model.EventUiModel
import com.folklore.app.presentation.model.FavoriteEventUiModel
import com.folklore.app.presentation.ui.view.events.EventItem

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
    onFavoriteEventClick: (event: FavoriteEventUiModel) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    FavoritesScreenContent(
        uiState = uiState,
        onFavoriteEventClick = onFavoriteEventClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreenContent(
    uiState: FavoritesUiState,
    onFavoriteEventClick: (event: FavoriteEventUiModel) -> Unit,
) {
    if (uiState.loading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.then(Modifier.size(32.dp))
            )
        }
    } else {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = stringResource(id = R.string.favorites_screen_title), fontSize = 24.sp)
            Spacer(modifier = Modifier.size(24.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 8.dp),
            ) {
                items(uiState.favorites) { event ->
                    FavoriteEventItem(
                        event = event,
                        onClicked = {
                            onFavoriteEventClick(
                                event
                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenContentPreview() {
    FavoritesScreenContent(
        uiState = FavoritesUiState(
            loading = false,
            favorites = listOf(
                FavoriteEventUiModel(
                    id = "xyz",
                    title = "Google i/o extended",
                    imageUrl = "https://io.google/2021/assets/io_social_asset.jpg",
                    location = "Corozal, Sucre",
                    startDate = "May 29 , 2034",
                ),
                FavoriteEventUiModel(
                    id = "xyz",
                    title = "Dev Fest",
                    imageUrl = "https://io.google/2021/assets/io_social_asset.jpg",
                    location = "Corozal, Sucre",
                    startDate = "May 29 , 2034",
                ),
            ),
        ),
        onFavoriteEventClick = {},
    )
}

@Preview(showSystemUi = true)
@Composable
fun FavoritesScreenContentLoadingPreview() {
    FavoritesScreenContent(
        uiState = FavoritesUiState(
            loading = true,
            favorites = listOf(
                FavoriteEventUiModel(
                    id = "xyz",
                    title = "Google i/o extended",
                    imageUrl = "https://io.google/2021/assets/io_social_asset.jpg",
                    location = "Corozal, Sucre",
                    startDate = "May 29 , 2034",
                ),
                FavoriteEventUiModel(
                    id = "xyz",
                    title = "Google i/o extended",
                    imageUrl = "https://io.google/2021/assets/io_social_asset.jpg",
                    location = "Corozal, Sucre",
                    startDate = "May 29 , 2034",
                ),
            ),
        ),
        onFavoriteEventClick = {},
    )
}
