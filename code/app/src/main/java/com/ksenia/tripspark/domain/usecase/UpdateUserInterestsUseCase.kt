package com.ksenia.tripspark.domain.usecase

import com.ksenia.tripspark.domain.model.Interest
import com.ksenia.tripspark.domain.repository.InterestRepository
import com.ksenia.tripspark.domain.repository.UserRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class UpdateUserInterestsUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val interestRepository: InterestRepository
) {
    suspend operator fun invoke(selected: List<Interest>) {
        val user = userRepository.getUser().firstOrNull() ?: return
        val updatedUser = user.copy(interests = selected)
        val selectedIds = selected.map { selected -> selected.id}
        userRepository.updateUser(updatedUser)
        interestRepository.syncUserInterests(selectedIds)
    }
}
