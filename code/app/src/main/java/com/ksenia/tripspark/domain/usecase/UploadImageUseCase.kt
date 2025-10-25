package com.ksenia.tripspark.domain.usecase

import com.ksenia.tripspark.domain.repository.ImageRepository

class UploadImageUseCase(
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(cityId: String, imageBytes: ByteArray, bucketKey: String): String {
        return imageRepository.uploadImage(cityId, imageBytes, bucketKey)
    }
}
