package com.ksenia.tripspark.data.datasource.remote

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.net.URLEncoder
import javax.inject.Inject
import javax.inject.Named

class ImageRemoteDataSource @Inject constructor(
    @Named("workerEndpoint") private val workerEndpoint: String
) {

    private val client = OkHttpClient()

    suspend fun uploadImage(imageBytes: ByteArray, fileName: String): String =
        withContext(Dispatchers.IO) {
            Log.d("UploadDebug", "imageBytes size: ${imageBytes.size}")
            val encodedFileName = URLEncoder.encode(fileName, "UTF-8")

            val request = Request.Builder()
                .url("$workerEndpoint?name=$encodedFileName")
                .post(imageBytes.toRequestBody("image/jpeg".toMediaType()))
                .addHeader("Content-Type", "image/jpeg")
                .build()

            val response = client.newCall(request).execute()

            if (!response.isSuccessful) {
                throw RuntimeException("Upload failed: ${response.code} ${response.message}")
            }

            return@withContext "$workerEndpoint?name=$encodedFileName"
        }

    fun getImageUrl(fileName: String): String {
        val encodedFileName = URLEncoder.encode(fileName, "UTF-8")
        return "$workerEndpoint?name=$encodedFileName"
    }
}
