package com.ksenia.tripspark.data.datasource

import androidx.room.TypeConverter
import javax.inject.Inject

class Converters@Inject constructor() {

    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toStringList(data: String): List<String> {
        return if (data.isEmpty()) emptyList() else data.split(",")
    }

    @TypeConverter
    fun fromVector(vector: List<Float>?): String {
        return vector?.joinToString(",") ?: ""
    }

    @TypeConverter
    fun toVector(data: String): List<Float> {
        return if (data.isBlank()) emptyList()
        else data.split(",").mapNotNull { it.toFloatOrNull() }
    }
}
