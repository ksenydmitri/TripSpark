package com.ksenia.tripspark.domain.repository

interface ImageRepository {
    suspend fun uploadImage(documentId: String, imageBytes: ByteArray, bucketKey: String): String
    suspend fun getImageUrl(documentId: String, bucketKey: String): String
}
