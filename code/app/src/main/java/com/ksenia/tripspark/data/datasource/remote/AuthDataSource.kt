package com.ksenia.tripspark.data.datasource.remote

import android.R.attr.password
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthDataSource@Inject constructor(
    private val auth: FirebaseAuth,
){
    fun getCurrentUserId(): String? = auth.currentUser?.uid

    suspend fun registerUser(email: String, password: String): String?{
        try {
            val result = auth.createUserWithEmailAndPassword(email,password).await()
            return result.user?.uid ?: throw IllegalStateException("Ошибка регистрации")
        } catch (e: Exception){
            throw e
        }
    }

    suspend fun loginUser(email: String, password: String): String?{
        val result = auth.signInWithEmailAndPassword(email,password).await()
        return result.user?.uid ?: throw IllegalStateException("Ошибка регистрации")
    }

    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    suspend fun logoutUser(){
        auth.signOut()
    }
}