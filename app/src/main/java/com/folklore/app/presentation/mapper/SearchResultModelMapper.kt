package com.folklore.app.presentation.mapper


import com.folklore.app.presentation.model.SearchResultModel
import com.folklore.domain.mapping.Mapper
import com.folklore.domain.model.Event
import javax.inject.Inject

class SearchResultModelMapper @Inject constructor() : Mapper<Event, SearchResultModel>() {

    override fun mapTo(value: Event): SearchResultModel {
        return SearchResultModel(
            title = value.title,
            id = value.id,
            imageUrl = value.imageUrl,
        )
    }
}
