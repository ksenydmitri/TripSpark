package com.ksenia.tripspark.data.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ksenia.tripspark.data.model.ContinentEntity
import com.ksenia.tripspark.data.model.InterestEntity

@Dao
interface InterestDao {

    @Query("SELECT * FROM interests")
    suspend fun getInterests(): List<InterestEntity>

    @Query("SELECT * FROM interests WHERE isChosen = 1")
    suspend fun getChosenInterests(): List<InterestEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllInterest(interests: List<InterestEntity>)

    @Query("UPDATE interests SET isChosen = :isChosen WHERE uid = :id")
    suspend fun updateInterestSelection(id: String, isChosen: Boolean)

    @Query("UPDATE interests SET isChosen = 0")
    suspend fun clearAllSelections()

    @Query("SELECT * FROM continents")
    suspend fun getContinents(): List<ContinentEntity>

    @Query("SELECT * FROM continents WHERE isChosen = 1")
    suspend fun getChosenContinents(): List<ContinentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllContinents(continents: List<ContinentEntity>)

    @Query("UPDATE continents SET isChosen = :isChosen WHERE uid = :id")
    suspend fun updateContinentSelection(id: String, isChosen: Boolean)

    @Query("UPDATE continents SET isChosen = 0")
    suspend fun clearAllContinentSelections()
}
