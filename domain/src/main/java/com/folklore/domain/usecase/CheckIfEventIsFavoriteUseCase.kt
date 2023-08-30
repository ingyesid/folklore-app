package com.folklore.domain.usecase

import com.folklore.domain.model.Event
import com.folklore.domain.repository.EventsRepository
import javax.inject.Inject

class CheckIfEventIsFavoriteUseCase @Inject constructor(
    private val eventsRepository: EventsRepository,
) {
    suspend operator fun invoke(event: Event): Boolean = eventsRepository.isFavorite(event)
}
