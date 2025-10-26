package com.ksenia.tripspark.domain.usecase.interests

import com.ksenia.tripspark.domain.repository.InterestRepository
import javax.inject.Inject

class GetChosenInterestsUseCase @Inject constructor(
    private val repository: InterestRepository
) {
    suspend operator fun invoke(): List<String> {
        return repository.getChosenInterests().map { it.id }
    }
}

