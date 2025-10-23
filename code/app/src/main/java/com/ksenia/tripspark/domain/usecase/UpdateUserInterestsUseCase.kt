package com.ksenia.tripspark.domain.usecase

import com.ksenia.tripspark.domain.model.Interest
import com.ksenia.tripspark.domain.repository.UserRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class UpdateUserInterestsUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(selected: List<Interest>) {
        val user = userRepository.getUser().firstOrNull()
            ?: throw IllegalStateException("User not found")

        val updatedUser = user.copy(interests = selected)
        userRepository.updateUser(updatedUser)
    }
}
