package com.ksenia.tripspark.data.repository

import android.util.Log
import android.util.Log.e
import com.ksenia.tripspark.data.datasource.local.UserDao
import com.ksenia.tripspark.data.datasource.remote.UserRemoteDataSource
import com.ksenia.tripspark.data.model.UserEntity
import com.ksenia.tripspark.domain.model.Interest
import com.ksenia.tripspark.domain.model.User
import com.ksenia.tripspark.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl@Inject constructor(
    private val localDataSource: UserDao,
    private val remoteDataSource: UserRemoteDataSource
) : UserRepository {
    override suspend fun getUser(): Flow<User?> {
        try {
            val localUser = localDataSource.getCurrentUser()
            return flow {
                User(
                    id = localUser?.uid ?: "",
                    name = localUser?.name ?: "",
                    email = localUser?.email ?: "",
                    imageId = localUser?.imageId ?: ""
                )
            }
        } catch (e: Exception){
            throw e;
        }
    }

    override suspend fun updateInterests() {
        TODO("Not yet implemented")
    }

    override suspend fun syncUserData() {
        try {
            val document = remoteDataSource.getUserData()
            if (document != null && document.exists()) {
                localDataSource.insertUser(
                    user = UserEntity(
                        uid = document.id,
                        name = document.getString("name"),
                        email = document.getString("email"),
                        imageId = document.getString("imageId") ?: ""
                    )
                )
            } else {
                return
            }
        } catch (e: Exception){
            throw e;
        }
    }

    override suspend fun loginUser() {
        TODO("Not yet implemented")
    }
}