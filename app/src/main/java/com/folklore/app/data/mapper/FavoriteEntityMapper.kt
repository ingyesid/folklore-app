package com.folklore.app.data.mapper

import com.folklore.app.data.database.model.FavoriteEntity
import com.folklore.app.domain.mapping.Mapper
import com.folklore.app.domain.model.Favorite
import javax.inject.Inject

class FavoriteEntityMapper @Inject constructor() : Mapper<FavoriteEntity, Favorite>() {

    override fun mapTo(value: FavoriteEntity): Favorite {
        return Favorite(
            title = value.title,
            id = value.id,
            imageUrl = value.imageUrl,
            location = value.location,
            startDate = value.startDate,
        )
    }

    override fun reverseTransform(value: Favorite): FavoriteEntity {
        return FavoriteEntity(
            title = value.title,
            id = value.id,
            imageUrl = value.imageUrl,
            location = value.location,
            startDate = value.startDate,
        )
    }

}
