package com.ksenia.tripspark.data.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ksenia.tripspark.data.model.InterestEntity

@Dao
interface InterestDao {

    @Query("SELECT * FROM interests")
    suspend fun getInterests(): List<InterestEntity>

    @Query("SELECT * FROM interests WHERE isChosen = 1")
    suspend fun getChosenInterests(): List<InterestEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(interests: List<InterestEntity>)

    @Query("UPDATE interests SET isChosen = :isChosen WHERE uid = :id")
    suspend fun updateInterestSelection(id: String, isChosen: Boolean)

    @Query("UPDATE interests SET isChosen = 0")
    suspend fun clearAllSelections()
}
