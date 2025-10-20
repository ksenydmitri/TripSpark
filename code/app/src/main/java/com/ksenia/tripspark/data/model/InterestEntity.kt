package com.ksenia.tripspark.data.model

import androidx.room.PrimaryKey

data class InterestEntity(
    @PrimaryKey val uid: String,
    val name: String
)
