package com.ksenia.tripspark.data.repository

import android.R.attr.name
import android.R.attr.password
import com.google.firebase.auth.FirebaseAuth
import com.ksenia.tripspark.data.datasource.remote.AuthDataSource
import com.ksenia.tripspark.data.datasource.remote.UserRemoteDataSource
import com.ksenia.tripspark.domain.model.User
import com.ksenia.tripspark.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl@Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository{
    override suspend fun loginUser(email: String, password: String) : String{
        try {
            val userId = authDataSource.loginUser(email, password)
            return userId ?: throw Exception("Ошибка авторизации пользователя 1")
        } catch(e: Exception){
            throw e
        }
    }

    override suspend fun registerUser(email: String,password: String): String {
        try {
            val userId = authDataSource.registerUser(email, password)
            return userId ?: throw Exception("userId is null")
        } catch (e: Exception){
            throw e
        }
    }

    override suspend fun getUserId(): String? {
        return authDataSource.getCurrentUserId()
    }

    override suspend fun logoutUser() {
        authDataSource.logoutUser()
    }

}