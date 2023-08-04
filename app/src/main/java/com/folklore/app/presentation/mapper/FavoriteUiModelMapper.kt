package com.folklore.app.presentation.mapper

import com.folklore.app.domain.mapping.Mapper
import com.folklore.app.domain.model.Event
import com.folklore.app.domain.model.Favorite
import com.folklore.app.domain.utils.ReadableTimeFormatter
import com.folklore.app.presentation.model.EventUiModel
import com.folklore.app.presentation.model.FavoriteEventUiModel
import com.folklore.app.presentation.model.SearchResultModel
import javax.inject.Inject

class FavoriteUiModelMapper @Inject constructor() : Mapper<Favorite, FavoriteEventUiModel>() {

    override fun mapTo(value: Favorite): FavoriteEventUiModel {
        return FavoriteEventUiModel(
            title = value.title,
            id = value.id,
            imageUrl = value.imageUrl,
            location = value.location,
            startDate = value.startDate,
        )
    }
}
