package com.ksenia.tripspark.domain.repository

import com.ksenia.tripspark.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser(): Flow<User?>
    suspend fun updateInterests(): Unit
    suspend fun syncUserData(): Unit
}