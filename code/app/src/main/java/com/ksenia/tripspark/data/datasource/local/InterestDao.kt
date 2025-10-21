package com.ksenia.tripspark.data.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ksenia.tripspark.data.model.InterestEntity

@Dao
interface InterestDao {
    @Query("SELECT * FROM interests WHERE userId = :userId ")
    suspend fun getInterestsByUserId(userId: String): List<InterestEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInterest(interest: InterestEntity): Unit
}