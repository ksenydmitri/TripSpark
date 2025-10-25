package com.ksenia.tripspark.domain.usecase

import com.ksenia.tripspark.domain.repository.ImageRepository
import com.ksenia.tripspark.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserAvatarUseCase@Inject constructor(
    private val userRepository: UserRepository,
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(userId: String) {
        val url = "avatar_${imageRepository.getImageUrl(userId)}.jpg"
        userRepository.updateUserAvatar(userId, url)
    }
}