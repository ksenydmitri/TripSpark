package com.ksenia.tripspark.data.repository

import com.ksenia.tripspark.data.datasource.local.ImageLocalDataSource
import com.ksenia.tripspark.data.datasource.remote.ImageRemoteDataSource
import com.ksenia.tripspark.domain.repository.ImageRepository
import java.io.File
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageRemoteDataSource: ImageRemoteDataSource,
    private val imageLocalDataSource: ImageLocalDataSource
) : ImageRepository {

    override suspend fun uploadImage(
        documentId: String,
        imageBytes: ByteArray,
        bucketKey: String
    ): String {
        val url = imageRemoteDataSource.uploadImage(imageBytes, documentId, bucketKey)
        imageLocalDataSource.saveImage("$documentId.jpg", imageBytes, permanent = true)

        return url
    }

    override suspend fun getImageUrl(documentId: String, bucketKey: String): String {
        return imageRemoteDataSource.getImageUrl(documentId, bucketKey)
    }

    override suspend fun getOrFetchImage(documentId: String, bucketKey: String): File {
        val fileName = "$documentId.jpg"

        val cached = imageLocalDataSource.getImageFile(fileName)
        if (cached != null) {
            return cached
        }
        val url = imageRemoteDataSource.getImageUrl(documentId, bucketKey)
        val bytes = imageRemoteDataSource.downloadImage(url)

        return imageLocalDataSource.saveImage(fileName, bytes)
    }

}
