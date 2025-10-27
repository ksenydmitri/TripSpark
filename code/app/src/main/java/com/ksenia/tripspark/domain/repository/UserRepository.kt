package com.ksenia.tripspark.domain.repository

import com.ksenia.tripspark.domain.model.Interest
import com.ksenia.tripspark.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser(): Flow<User?>
    suspend fun updateUser(user: User)
    suspend fun syncUserData(): Unit
    suspend fun createUser(user: User): Unit
    suspend fun logoutUser(userId: String)
    suspend fun updateUserAvatar(userId: String, avatarUrl: String)
}