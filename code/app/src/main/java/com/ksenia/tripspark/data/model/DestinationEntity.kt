package com.ksenia.tripspark.data.model

import android.location.Location
import androidx.room.PrimaryKey
import com.ksenia.tripspark.domain.model.Note

data class DestinationEntity(
    @PrimaryKey val uid: String,
    val name: String,
    val description: String,
    val location: Location,
    val imageUrl: String,
    val note: Note?
)