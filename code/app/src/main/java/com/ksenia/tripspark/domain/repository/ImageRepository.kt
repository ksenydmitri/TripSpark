package com.ksenia.tripspark.domain.repository

interface ImageRepository {
    suspend fun uploadImage(documentId: String, imageBytes: ByteArray): String
    suspend fun getImageUrl(documentId: String): String
}
