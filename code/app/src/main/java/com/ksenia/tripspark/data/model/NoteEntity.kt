package com.ksenia.tripspark.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey val uid: String,
    val description: String,
    val text: String,
    val destinationId: String
)
