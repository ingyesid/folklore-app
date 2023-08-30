package com.folklore.domain.usecase

import com.folklore.domain.repository.EventsRepository
import javax.inject.Inject

class GetAllEventsUseCase @Inject constructor(
    private val eventsRepository: EventsRepository,
) {
    operator fun invoke() = eventsRepository.getAllEvents()
}
