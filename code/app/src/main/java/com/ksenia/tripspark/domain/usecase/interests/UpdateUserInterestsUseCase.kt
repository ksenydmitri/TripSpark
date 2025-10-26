package com.ksenia.tripspark.domain.usecase.interests

import com.ksenia.tripspark.domain.repository.InterestRepository
import javax.inject.Inject

class UpdateUserInterestsUseCase @Inject constructor(
    private val repository: InterestRepository
) {
    suspend operator fun invoke(userId: String, selectedIds: List<String>) {
        repository.syncUserInterests(userId, selectedIds)
    }
}
