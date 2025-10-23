package com.ksenia.tripspark.domain.usecase

import com.ksenia.tripspark.domain.model.Interest
import com.ksenia.tripspark.domain.repository.InterestRepository
import javax.inject.Inject

class GetInterestsUseCase@Inject constructor(
    private val interestRepository: InterestRepository
) {
    suspend operator fun invoke(): List<Interest>{
        return interestRepository.getInterests()
    }
}