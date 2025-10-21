package com.ksenia.tripspark.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ksenia.tripspark.data.model.DestinationEntity

@Dao
interface WishlistDao {
    @Query("SELECT * FROM destinations WHERE userId = :userId")
    suspend fun getDestinationsByUserId(userId: String): List<DestinationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDestination(destinationEntity: DestinationEntity): Unit
}