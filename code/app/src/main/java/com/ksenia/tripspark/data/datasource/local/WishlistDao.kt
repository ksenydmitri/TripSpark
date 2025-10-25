package com.ksenia.tripspark.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ksenia.tripspark.data.model.WishlistItemEntity

@Dao
interface WishlistDao {

    @Query("SELECT * FROM wishlist WHERE userId = :userId")
    suspend fun getWishlist(userId: String): List<WishlistItemEntity>

    @Query("SELECT * FROM wishlist WHERE userId = :userId AND destinationId = :destinationId LIMIT 1")
    suspend fun getWishlistItem(userId: String, destinationId: String): WishlistItemEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWishlistItem(item: WishlistItemEntity)

    @Query("DELETE FROM wishlist WHERE userId = :userId AND destinationId = :destinationId")
    suspend fun deleteWishlistItem(userId: String, destinationId: String)

    @Query("UPDATE wishlist SET note = :noteText WHERE userId = :userId AND destinationId = :destinationId")
    suspend fun updateNote(userId: String, destinationId: String, noteText: String)

    @Query("SELECT note FROM wishlist WHERE userId = :userId AND destinationId = :destinationId LIMIT 1")
    suspend fun getNoteText(userId: String, destinationId: String): String?

    @Query("UPDATE wishlist SET note = '' WHERE userId = :userId AND destinationId = :destinationId")
    suspend fun clearNote(userId: String, destinationId: String)

    @Query("SELECT * FROM wishlist WHERE userId = :userId AND note != ''")
    suspend fun getAllNotes(userId: String): List<WishlistItemEntity>
}