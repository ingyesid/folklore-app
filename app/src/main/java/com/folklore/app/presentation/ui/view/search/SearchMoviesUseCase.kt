package com.folklore.app.presentation.ui.view.search

import com.folklore.domain.model.Event
import com.folklore.domain.repository.EventsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val eventsRepository: EventsRepository,
) {
    suspend fun execute(query: String): List<Event> =
        withContext(Dispatchers.IO) {
            if (query.isEmpty()) return@withContext emptyList()
            return@withContext eventsRepository.searchEvents("%$query%")
        }
}
