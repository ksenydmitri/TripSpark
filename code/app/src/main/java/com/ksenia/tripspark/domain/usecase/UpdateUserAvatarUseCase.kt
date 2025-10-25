package com.ksenia.tripspark.domain.usecase

import com.ksenia.tripspark.domain.repository.ImageRepository
import com.ksenia.tripspark.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserAvatarUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(userId: String) {
        val fileName = "avatar_$userId.jpg"
        val url = imageRepository.getImageUrl(fileName, bucketKey = "CITY_IMAGES")
        userRepository.updateUserAvatar(userId, url)
    }
}