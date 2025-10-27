package com.ksenia.tripspark.domain.usecase

import android.util.Log
import com.google.android.play.integrity.internal.u
import com.ksenia.tripspark.domain.model.User
import com.ksenia.tripspark.domain.repository.AuthRepository
import com.ksenia.tripspark.domain.repository.UserRepository
import javax.inject.Inject

class LoginUserWithEmailAndPasswordUseCase@Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): String{
        try {
            val userId = authRepository.loginUser(email,password)
            userRepository.syncUserData()
            return userId
        } catch (e: Exception){
            Log.e("Login",e.message ?: "")
            throw e
        }
    }
}