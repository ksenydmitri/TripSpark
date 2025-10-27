package com.ksenia.tripspark.domain.usecase

import android.util.Log
import com.ksenia.tripspark.domain.model.User
import com.ksenia.tripspark.domain.repository.AuthRepository
import com.ksenia.tripspark.domain.repository.UserRepository
import javax.inject.Inject

class RegisterUserUseCase@Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String, name: String): String{
        try {
            val userId = authRepository.registerUser(email,password)
            val user = User(id = userId,
                email = email,
                name = name,
                imageId = "",
                interests = emptyList()
            )
            userRepository.createUser(user)
            userRepository.syncUserData()
            return userId
        } catch (e: Exception){
            Log.e("Register",e.message ?: "")
            throw e
        }
    }
}