package com.ksenia.tripspark.domain.model

data class User(
    val id: String,
    val name: String,
    val interests: List<Interest>,
    val wishlist: List<Destination>,
    val imageId: String
)
