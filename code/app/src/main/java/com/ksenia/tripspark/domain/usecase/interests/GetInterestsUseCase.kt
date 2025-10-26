package com.ksenia.tripspark.domain.usecase.interests

import com.ksenia.tripspark.domain.model.Interest
import com.ksenia.tripspark.domain.repository.InterestRepository
import javax.inject.Inject

class GetInterestsUseCase @Inject constructor(
    private val repository: InterestRepository
) {
    suspend operator fun invoke(): List<Interest> {
        return repository.getInterests()
    }
}
