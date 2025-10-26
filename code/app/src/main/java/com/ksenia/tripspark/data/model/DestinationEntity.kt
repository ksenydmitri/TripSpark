package com.ksenia.tripspark.data.model

import android.location.Location
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ksenia.tripspark.domain.model.Note

@Entity(tableName = "destinations")
data class DestinationEntity(
    @PrimaryKey val uid: String,
    val name: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val imageUrl: String,
    val rating: Double,
    val vector: String
)