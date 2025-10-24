package com.ksenia.tripspark.di.modules

import android.content.Context
import androidx.room.Room
import com.ksenia.tripspark.data.datasource.local.AppDatabase
import com.ksenia.tripspark.data.datasource.local.DestinationDao
import com.ksenia.tripspark.data.datasource.local.InterestDao
import com.ksenia.tripspark.data.datasource.local.UserDao
import com.ksenia.tripspark.data.datasource.local.WishlistDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideInterestDao(database: AppDatabase): InterestDao {
        return database.interestDao()
    }

    @Provides
    fun provideWishlistDao(database: AppDatabase): WishlistDao {
        return database.wishlistDao()
    }

    @Provides
    fun provideDestination(database: AppDatabase): DestinationDao {
        return database.destinationDao()
    }
}