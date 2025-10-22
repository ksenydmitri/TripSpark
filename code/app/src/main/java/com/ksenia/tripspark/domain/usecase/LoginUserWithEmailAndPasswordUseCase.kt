package com.ksenia.tripspark.domain.usecase

import android.util.Log
import com.ksenia.tripspark.domain.model.User
import com.ksenia.tripspark.domain.repository.AuthRepository
import com.ksenia.tripspark.domain.repository.UserRepository
import javax.inject.Inject

class LoginUserWithEmailAndPasswordUseCase@Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String){
        val userId = authRepository.loginUser(email,password)
        try {
            userRepository.createUser(
                User(id = userId,
                    email = email,
                    name = "",
                    imageId = ""
                ))
            userRepository.syncUserData()
        } catch (e: Exception){
            Log.e("Login",e.message ?: "")
            throw e
        }
    }
}