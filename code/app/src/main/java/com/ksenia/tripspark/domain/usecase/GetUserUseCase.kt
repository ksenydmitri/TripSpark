package com.ksenia.tripspark.domain.usecase

import com.ksenia.tripspark.domain.model.User
import com.ksenia.tripspark.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(): Flow<User?> {
        return userRepository.getUser()
    }
}