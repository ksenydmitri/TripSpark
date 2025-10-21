package com.ksenia.tripspark.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "interests")
data class InterestEntity(
    @PrimaryKey val uid: String,
    val userId: String,
    val name: String
)
