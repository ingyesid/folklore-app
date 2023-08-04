package com.folklore.app.presentation.utils

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.folklore.app.presentation.model.EventUiModel

class EventsPreviewProvider : PreviewParameterProvider<EventUiModel> {
    override val values: Sequence<EventUiModel> = sequenceOf(
        EventUiModel(
            id = "xyz",
            title = "Google i/o extended",
            imageUrl = "https://io.google/2021/assets/io_social_asset.jpg",
            goingCount = 20,
            likes = 30,
            location = "Corozal, Sucre",
            startDate = "May 29 , 2034",
        ),
    )
}
