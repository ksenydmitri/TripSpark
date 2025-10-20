package com.ksenia.tripspark.data.model

import androidx.room.PrimaryKey

data class NoteEntity(
    @PrimaryKey val uid: String,
    val description: String,
    val text: String
)
