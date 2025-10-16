package com.ksenia.tripspark.domain.model

data class Recommendation(
    val id: String,
    val destinationId: String,
    val relevance: Float
)
