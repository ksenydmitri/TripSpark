package com.ksenia.tripspark.di.modules

import com.ksenia.tripspark.data.datasource.local.UserDao
import com.ksenia.tripspark.data.datasource.remote.AuthDataSource
import com.ksenia.tripspark.data.datasource.remote.UserRemoteDataSource
import com.ksenia.tripspark.data.repository.AuthRepositoryImpl
import com.ksenia.tripspark.data.repository.UserRepositoryImpl
import com.ksenia.tripspark.domain.repository.AuthRepository
import com.ksenia.tripspark.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideUserRepository(
        localDataSource: UserDao,
        remoteDataSource: UserRemoteDataSource
    ): UserRepository{
        return UserRepositoryImpl(
            localDataSource,remoteDataSource)
    }

    @Provides
    fun provideAuthRepository(
        authDataSource: AuthDataSource
    ) : AuthRepository{
        return AuthRepositoryImpl(authDataSource)
    }
}