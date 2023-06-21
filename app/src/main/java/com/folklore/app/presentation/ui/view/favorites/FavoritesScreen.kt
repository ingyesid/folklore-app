package com.folklore.app.presentation.ui.view.favorites

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.folklore.app.R
import com.folklore.app.presentation.model.EventUiModel
import com.folklore.app.presentation.model.FavoriteEventUiModel

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
    onFavoriteEventClick: (event: FavoriteEventUiModel) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsState().value
    FavoritesScreenContent(
        uiState = uiState,
        onFavoriteEventClick = { onFavoriteEventClick },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreenContent(
    uiState: FavoritesUiState,
    onFavoriteEventClick: (event: EventUiModel) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.home_screen_title),
                        maxLines = 2,
                    )
                },
            )
        },
    ) { contentPadding ->

    }
}

@Preview
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
