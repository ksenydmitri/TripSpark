package com.ksenia.tripspark.domain.usecase

import com.ksenia.tripspark.domain.repository.AuthRepository
import com.ksenia.tripspark.domain.repository.UserRepository
import javax.inject.Inject

class LogoutUseCase@Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String?){
        try {
            userRepository.logoutUser(userId
                ?: throw Exception("user is null"))
            authRepository.logoutUser()
        } catch (e: Exception){
            throw e
        }
    }
}