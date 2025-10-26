package com.ksenia.tripspark.domain.usecase.interests

import com.ksenia.tripspark.domain.model.Continent
import com.ksenia.tripspark.domain.repository.InterestRepository
import javax.inject.Inject

class GetContinentsUseCase @Inject constructor(
    private val repository: InterestRepository
) {
    suspend operator fun invoke(): List<Continent> {
        return repository.getContinents()
    }
}
