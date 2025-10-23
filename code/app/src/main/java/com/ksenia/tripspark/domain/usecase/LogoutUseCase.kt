package com.ksenia.tripspark.domain.usecase

import com.ksenia.tripspark.domain.repository.AuthRepository
import com.ksenia.tripspark.domain.repository.UserRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class LogoutUseCase@Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(){
        val user = userRepository.getUser().firstOrNull() ?: return
        userRepository.logoutUser(user)
       authRepository.logoutUser()
    }
}