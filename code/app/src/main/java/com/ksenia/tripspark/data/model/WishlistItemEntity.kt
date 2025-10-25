package com.ksenia.tripspark.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishlist")
data class WishlistItemEntity(
    @PrimaryKey val destinationId: String,
    val userId: String,
    val note: String,
    val addedAt: Long
)
