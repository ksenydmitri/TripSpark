package com.ksenia.tripspark.domain.repository

import com.ksenia.tripspark.domain.model.Interest

interface InterestRepository {
    suspend fun getInterests(): List<Interest>
    suspend fun syncUserInterests(selectedIds: List<String>)
    suspend fun getChosenInterests(): List<Interest>
}