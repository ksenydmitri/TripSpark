package com.ksenia.tripspark.domain.repository

import com.ksenia.tripspark.domain.model.Destination

interface DestinationRepository {
    suspend fun getAllDestinations(): List<Destination>
}
