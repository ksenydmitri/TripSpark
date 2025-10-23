package com.ksenia.tripspark.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ksenia.tripspark.data.datasource.Converters
import com.ksenia.tripspark.data.model.DestinationEntity
import com.ksenia.tripspark.data.model.InterestEntity
import com.ksenia.tripspark.data.model.NoteEntity
import com.ksenia.tripspark.data.model.UserEntity

@Database(entities = [
    UserEntity::class,
    InterestEntity::class,
    NoteEntity::class,
    DestinationEntity::class], version = 2,exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun interestDao(): InterestDao
    abstract fun wishlistDao(): WishlistDao
}
