package com.ksenia.tripspark.domain.repository

import java.io.File

interface ImageRepository {
    suspend fun uploadImage(documentId: String, imageBytes: ByteArray, bucketKey: String): String
    suspend fun getImageUrl(documentId: String, bucketKey: String): String
    suspend fun getOrFetchImage(documentId: String, bucketKey: String): File
}
