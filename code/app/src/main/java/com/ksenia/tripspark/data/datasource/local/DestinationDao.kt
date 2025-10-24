package com.ksenia.tripspark.data.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ksenia.tripspark.data.model.DestinationEntity

@Dao
interface DestinationDao {

    @Query("SELECT * FROM destinations")
    suspend fun getAllDestinations(): List<DestinationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(destinations: List<DestinationEntity>)

    @Update
    suspend fun updateDestination(destination: DestinationEntity)

    @Delete
    suspend fun deleteDestinations(destinations: List<DestinationEntity>)
}