package com.ksenia.tripspark.domain.repository

interface AuthRepository {
    suspend fun loginUser(email: String, password: String): String
    suspend fun registerUser(email: String, password: String): String
    suspend fun getUserId(): String?
}