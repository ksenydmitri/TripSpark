package com.ksenia.tripspark.di.modules

import com.ksenia.tripspark.data.datasource.local.DestinationDao
import com.ksenia.tripspark.data.datasource.local.InterestDao
import com.ksenia.tripspark.data.datasource.local.UserDao
import com.ksenia.tripspark.data.datasource.remote.AuthDataSource
import com.ksenia.tripspark.data.datasource.remote.DestinationRemoteDataSource
import com.ksenia.tripspark.data.datasource.remote.InterestDataSource
import com.ksenia.tripspark.data.datasource.remote.UserRemoteDataSource
import com.ksenia.tripspark.data.repository.AuthRepositoryImpl
import com.ksenia.tripspark.data.repository.DestinationRepositoryImpl
import com.ksenia.tripspark.data.repository.InterestRepositoryImpl
import com.ksenia.tripspark.data.repository.UserRepositoryImpl
import com.ksenia.tripspark.domain.repository.AuthRepository
import com.ksenia.tripspark.domain.repository.InterestRepository
import com.ksenia.tripspark.domain.repository.DestinationRepository
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

    @Provides
    fun provideInterestRepository(
        remoteDataSource: InterestDataSource,
        localDataSource: InterestDao
    ): InterestRepository{
        return InterestRepositoryImpl(remoteDataSource,localDataSource)
    }

    @Provides
    fun provideRecommendationRepository(
        destinationLocalDataSource: DestinationDao,
        destinationRemoteDataSource: DestinationRemoteDataSource
    ): DestinationRepository {
        return DestinationRepositoryImpl(
            destinationLocalDataSource,
            destinationRemoteDataSource)
    }
}