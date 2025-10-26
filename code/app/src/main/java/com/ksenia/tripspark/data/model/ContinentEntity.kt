package com.ksenia.tripspark.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "continents")
data class ContinentEntity(
    @PrimaryKey val uid: String,
    val name: String?,
    val centerLat: Double?,
    val centerLon: Double?,
    val screenXPercent: Float,
    val screenYPercent: Float,
    val isChosen: Boolean = false
)
