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
            return flow { emit(User(
                id = localUser?.uid ?: "",
                name = localUser?.name ?: "",
                email = localUser?.email ?: "",
                imageId = localUser?.imageId ?: "",
                interests = emptyList()))
            }
        } catch (e: Exception){
            throw e;
        }
    }

    override suspend fun updateUser(user: User) {
        try {
            remoteDataSource.updateUser(user)
        } catch (e: Exception){
            throw e
        }
    }

    override suspend fun syncUserData() {
        try {
            val document = remoteDataSource.getUserData()
            if (document != null && document.exists()) {
                val user = UserEntity(
                    uid = "local_user",
                    name = document.getString("name"),
                    email = document.getString("email"),
                    imageId = document.getString("imageId") ?: ""
                )
              localDataSource.insertUser(user)
            } else {
                return
            }
        } catch (e: Exception){
            throw e;
        }
    }

    override suspend fun createUser(user: User) {
        try {
            remoteDataSource.createUser(user =
                User(id = user.id,
                    name = user.name,
                    email = user.email,
                    imageId = user.imageId,
                    interests = user.interests
                ))
        } catch (e: Exception){
            throw Exception("Ошибка авторизации пользователя")
        }
    }

    override suspend fun logoutUser(user: User){
        localDataSource.deleteUser(UserEntity(uid = user.id,
            name = user.name,
            email = user.email,
            imageId = user.imageId
        ))
    }
}