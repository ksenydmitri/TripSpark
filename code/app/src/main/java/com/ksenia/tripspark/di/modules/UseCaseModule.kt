package com.ksenia.tripspark.di.modules

import com.ksenia.tripspark.domain.repository.AuthRepository
import com.ksenia.tripspark.domain.repository.InterestRepository
import com.ksenia.tripspark.domain.repository.UserRepository
import com.ksenia.tripspark.domain.usecase.GetInterestsUseCase
import com.ksenia.tripspark.domain.usecase.GetUserUseCase
import com.ksenia.tripspark.domain.usecase.LoginUserWithEmailAndPasswordUseCase
import com.ksenia.tripspark.domain.usecase.RegisterUserUseCase
import com.ksenia.tripspark.domain.usecase.UpdateLocalUserUseCase
import com.ksenia.tripspark.domain.usecase.UpdateUserInterestsUseCase
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

    @Provides
    fun provideGetInterestsUseCase(
        interestRepository: InterestRepository
    ): GetInterestsUseCase{
        return GetInterestsUseCase(interestRepository)
    }

    @Provides
    fun provideUpdateUserInterestsUseCase(
        userRepository: UserRepository
    ): UpdateUserInterestsUseCase{
        return UpdateUserInterestsUseCase(
            userRepository
        )
    }
}