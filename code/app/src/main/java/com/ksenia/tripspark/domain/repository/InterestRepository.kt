package com.ksenia.tripspark.domain.repository

import com.ksenia.tripspark.domain.model.Continent
import com.ksenia.tripspark.domain.model.Interest

interface InterestRepository {
    suspend fun getInterests(): List<Interest>
    suspend fun getChosenInterests(): List<Interest>
    suspend fun getChosenContinents(): List<Continent>
    suspend fun getContinents(): List<Continent>
    suspend fun syncUserInterests(userId: String, selectedIds: List<String>)
    suspend fun syncUserContinents(userId: String, selectedIds: List<String>)

}