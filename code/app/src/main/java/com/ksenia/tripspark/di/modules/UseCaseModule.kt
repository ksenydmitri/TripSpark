package com.ksenia.tripspark.di.modules

import com.ksenia.tripspark.domain.repository.AuthRepository
import com.ksenia.tripspark.domain.repository.UserRepository
import com.ksenia.tripspark.domain.usecase.GetUserUseCase
import com.ksenia.tripspark.domain.usecase.LoginUserWithEmailAndPasswordUseCase
import com.ksenia.tripspark.domain.usecase.RegisterUserUseCase
import com.ksenia.tripspark.domain.usecase.UpdateLocalUserUseCase
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

    @Provides
    fun provideLoginUserWithEmailAndPasswordUseCase(
        userRepository: UserRepository,
        authRepository: AuthRepository
    ): LoginUserWithEmailAndPasswordUseCase{
        return LoginUserWithEmailAndPasswordUseCase(
            authRepository,userRepository
        )
    }

    @Provides
    fun provideRegisterUseCase(
        userRepository: UserRepository,
        authRepository: AuthRepository
    ): RegisterUserUseCase {
        return RegisterUserUseCase(
            authRepository,userRepository
        )
    }

    @Provides
    fun provideUpdateLocalUserUseCase(
        userRepository: UserRepository
    ): UpdateLocalUserUseCase{
        return UpdateLocalUserUseCase(userRepository)
    }
}