package com.folklore.domain.usecase

import com.folklore.domain.repository.EventsRepository
import javax.inject.Inject

class GetEventByIdUseCase @Inject constructor(
    private val eventsRepository: EventsRepository,
) {
    operator fun invoke(id: String) = eventsRepository.getEvent(id)
}
