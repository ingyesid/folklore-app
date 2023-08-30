package com.folklore.app.presentation.mapper

import com.folklore.app.presentation.model.FavoriteEventUiModel
import com.folklore.domain.mapping.Mapper
import com.folklore.domain.model.Favorite
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
