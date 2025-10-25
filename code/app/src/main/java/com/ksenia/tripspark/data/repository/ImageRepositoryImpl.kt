package com.ksenia.tripspark.data.repository

import com.ksenia.tripspark.data.datasource.remote.ImageRemoteDataSource
import com.ksenia.tripspark.domain.repository.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageRemoteDataSource: ImageRemoteDataSource
) : ImageRepository {

    override suspend fun uploadImage(documentId: String, imageBytes: ByteArray, bucketKey: String): String {
        return imageRemoteDataSource.uploadImage(imageBytes, documentId, bucketKey)
    }

    override suspend fun getImageUrl(documentId: String, bucketKey: String): String {
        return imageRemoteDataSource.getImageUrl(documentId, bucketKey)
    }
}
