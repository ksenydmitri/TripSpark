package com.ksenia.tripspark.domain.repository

import com.ksenia.tripspark.domain.model.Note
import com.ksenia.tripspark.domain.model.WishlistItem

interface WishlistRepository {
    suspend fun addToWishlist(userId: String, item: WishlistItem)
    suspend fun removeFromWishlist(userId: String, destinationId: String)
    suspend fun getWishlist(userId: String): List<WishlistItem>
    suspend fun addNote(userId: String, destinationId: String, note: Note)
    suspend fun updateNote(userId: String, destinationId: String, note: Note)
    suspend fun deleteNote(userId: String, destinationId: String, noteId: String)
    suspend fun getNoteForDestination(userId: String, destinationId: String): Note
}
