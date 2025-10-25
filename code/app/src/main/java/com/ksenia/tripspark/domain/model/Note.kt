package com.ksenia.tripspark.domain.model

data class Note (
    val id: String,
    val userId: String,
    val destinationId: String,
    val text: String,
    val status: String
)