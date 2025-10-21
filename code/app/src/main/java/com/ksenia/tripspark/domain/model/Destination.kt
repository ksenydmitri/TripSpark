package com.ksenia.tripspark.domain.model

import android.location.Location

data class Destination (
    val id: String,
    val name: String,
    val description: String,
    val location: Location,
    val imageUrl: String
    )