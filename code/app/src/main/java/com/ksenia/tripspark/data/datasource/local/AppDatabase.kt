package com.ksenia.tripspark.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ksenia.tripspark.data.datasource.Converters
import com.ksenia.tripspark.data.model.UserEntity

@Database(entities = [UserEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
