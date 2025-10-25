package com.ksenia.tripspark.domain.model

data class WishlistItem(
    val destinationId: String = "",
    val name: String = "",
    var note: String = "",
    val addedAt: Long = 0
)

