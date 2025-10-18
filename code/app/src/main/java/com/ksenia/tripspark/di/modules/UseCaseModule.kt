package com.ksenia.tripspark.di.modules

import com.ksenia.tripspark.domain.repository.UserRepository
import com.ksenia.tripspark.domain.usecase.GetUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetUserUseCase(
        userRepository: UserRepository
    ): GetUserUseCase{
        return GetUserUseCase(userRepository)
    }
}