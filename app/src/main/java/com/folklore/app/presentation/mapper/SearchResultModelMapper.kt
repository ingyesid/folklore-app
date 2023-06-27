package com.folklore.app.presentation.mapper

import com.folklore.app.domain.mapping.Mapper
import com.folklore.app.domain.model.Event
import com.folklore.app.domain.utils.ReadableTimeFormatter
import com.folklore.app.presentation.model.EventUiModel
import com.folklore.app.presentation.model.SearchResultModel
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
