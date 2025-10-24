package com.ksenia.tripspark.domain.model

data class Recommendation(
    val destination: Destination,
    val relevance: Float
)
