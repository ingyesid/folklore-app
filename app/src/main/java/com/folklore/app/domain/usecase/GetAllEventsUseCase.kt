package com.folklore.app.domain.usecase

import com.folklore.app.domain.repository.EventsRepository
import javax.inject.Inject

class GetAllEventsUseCase @Inject constructor(
    private val eventsRepository: EventsRepository,
) {
    operator fun invoke() = eventsRepository.getAllEvents()
}
