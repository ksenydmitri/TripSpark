package com.ksenia.tripspark.data.datasource.local

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class ImageLocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun saveImage(fileName: String, bytes: ByteArray, permanent: Boolean = true): File {
        val directory = if (permanent) {
            File(context.filesDir, "images")
        } else {
            File(context.cacheDir, "images")
        }

        if (!directory.exists()) directory.mkdirs()

        val file = File(directory, fileName)

        FileOutputStream(file).use { out ->
            out.write(bytes)
        }

        return file
    }

    fun loadImage(fileName: String, permanent: Boolean = true): Bitmap? {
        val directory = if (permanent) {
            File(context.filesDir, "images")
        } else {
            File(context.cacheDir, "images")
        }

        val file = File(directory, fileName)
        if (!file.exists()) return null

        return BitmapFactory.decodeFile(file.absolutePath)
    }

    fun getImageFile(fileName: String, permanent: Boolean = true): File? {
        val directory = if (permanent) {
            File(context.filesDir, "images")
        } else {
            File(context.cacheDir, "images")
        }

        val file = File(directory, fileName)
        return if (file.exists()) file else null
    }
}
