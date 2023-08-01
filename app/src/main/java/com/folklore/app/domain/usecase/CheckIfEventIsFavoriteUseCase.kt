package com.folklore.app.domain.usecase

import com.folklore.app.domain.model.Event
import com.folklore.app.domain.repository.EventsRepository
import javax.inject.Inject

class CheckIfEventIsFavoriteUseCase @Inject constructor(
    private val eventsRepository: EventsRepository,
) {
    suspend operator fun invoke(event: Event): Boolean = eventsRepository.isFavorite(event)
}
