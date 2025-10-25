package com.ksenia.tripspark.data.repository

import android.util.Log
import com.ksenia.tripspark.data.datasource.local.WishlistDao
import com.ksenia.tripspark.data.datasource.remote.WishlistRemoteDataStore
import com.ksenia.tripspark.data.model.WishlistItemEntity
import com.ksenia.tripspark.domain.model.Note
import com.ksenia.tripspark.domain.model.WishlistItem
import com.ksenia.tripspark.domain.repository.WishlistRepository
import javax.inject.Inject

class WishlistRepositoryImpl @Inject constructor(
    private val localDataSource: WishlistDao,
    private val remoteDataSource: WishlistRemoteDataStore
) : WishlistRepository {

    override suspend fun addToWishlist(userId: String, item: WishlistItem) {
        localDataSource.insertWishlistItem(WishlistItemEntity(
            destinationId = item.destinationId,
            userId = userId,
            note = item.note,
            addedAt = item.addedAt
        ))
        Log.e("ADD TO WISHLIST", "add item ${item.name} for user ${userId}")
        remoteDataSource.addToWishlist(userId, item)
    }

    override suspend fun removeFromWishlist(userId: String, destinationId: String) {
        localDataSource.deleteWishlistItem(userId, destinationId)
        remoteDataSource.removeFromWishlist(userId, destinationId)
    }

    override suspend fun getWishlist(userId: String): List<WishlistItem> {
        val localItems = localDataSource.getWishlist(userId).map { WishlistItem(
            destinationId = it.destinationId,
            note = it.note,
            addedAt = it.addedAt,
            name = it.destinationId
        ) }
        val remoteItems = remoteDataSource.getWishlist(userId)
        return (localItems + remoteItems).distinctBy { it.destinationId }
    }

    override suspend fun addNote(userId: String, destinationId: String, note: Note) {
        updateNote(userId, destinationId, note)
    }

    override suspend fun updateNote(userId: String, destinationId: String, note: Note) {
        val current = localDataSource.getWishlistItem(userId, destinationId)
        if (current != null) {
            localDataSource.insertWishlistItem(current.copy(note = note.text))
        }
        remoteDataSource.updateNote(userId, destinationId, note)
    }

    override suspend fun deleteNote(userId: String, destinationId: String, noteId: String) {
        val deletedNote = Note(
            id = noteId, text = "",
            status = "deleted",
            userId = userId,
            destinationId = destinationId)
        updateNote(userId, destinationId, deletedNote)
    }

    override suspend fun getNoteForDestination(userId: String, destinationId: String): Note {
        val localNote = localDataSource.getWishlistItem(userId, destinationId)?.note
        return if (!localNote.isNullOrBlank()) {
            Note(id = destinationId,
                text = localNote,
                status = "active",
                userId = userId,
                destinationId = destinationId)
        } else {
            remoteDataSource.getNoteForDestination(userId, destinationId)
        }
    }
}
