package com.ksenia.tripspark.domain.usecase

import com.ksenia.tripspark.domain.repository.UserRepository
import javax.inject.Inject

class UpdateLocalUserUseCase@Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(){
        try {
            userRepository.syncUserData()
        }catch (e: Exception){
            throw Exception("Ошибка синхронизации")
        }
    }
}