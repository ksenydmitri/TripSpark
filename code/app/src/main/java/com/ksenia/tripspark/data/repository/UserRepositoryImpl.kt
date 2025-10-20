package com.ksenia.tripspark.data.repository

import com.ksenia.tripspark.domain.model.Interest
import com.ksenia.tripspark.domain.model.User
import com.ksenia.tripspark.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl : UserRepository {
    override suspend fun getUser(): Flow<User?> {
        return flow { emit(
            User(id = "first",
                name = "User",
                interests = listOf(
                    Interest(id = "1", name = "sities"),
                    Interest(id = "2", name = "nature")
                ),
                wishlist = emptyList(),
                imageId = "")
        ) }
    }

    override suspend fun updateInterests() {
        TODO("Not yet implemented")
    }

    override suspend fun syncUserData() {
        TODO("Not yet implemented")
    }
}