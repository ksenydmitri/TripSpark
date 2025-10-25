package com.ksenia.tripspark.data.datasource.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.ksenia.tripspark.domain.model.Note
import com.ksenia.tripspark.domain.model.WishlistItem
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WishlistRemoteDataStore @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    private fun wishlistCollection(userId: String) =
        firestore.collection("users")
            .document(userId)
            .collection("wishlist")

    suspend fun addToWishlist(userId: String, item: WishlistItem) {
        Log.e("ADD TO WISHLIST", "add item ${item.name} for user ${userId}")
        wishlistCollection(userId)
            .document(item.destinationId)
            .set(item)
            .await()
    }

    suspend fun removeFromWishlist(userId: String, destinationId: String) {
        wishlistCollection(userId)
            .document(destinationId)
            .delete()
            .await()
    }

    suspend fun getWishlist(userId: String): List<WishlistItem> {
        val snapshot = wishlistCollection(userId).get().await()
        return snapshot.documents.mapNotNull { it.toObject(WishlistItem::class.java) }
    }

    suspend fun updateNote(userId: String, destinationId: String, note: Note) {
        wishlistCollection(userId)
            .document(destinationId)
            .update("note", note.text)
            .await()
    }

    suspend fun getNoteForDestination(userId: String, destinationId: String): Note {
        val snapshot = wishlistCollection(userId)
            .document(destinationId)
            .get()
            .await()
        val item = snapshot.toObject(WishlistItem::class.java)
        return Note(
            id = destinationId,
            text = item?.note ?: "",
            status = "active",
            userId = userId,
            destinationId = destinationId)
    }

    suspend fun addNote(userId: String, destinationId: String, note: Note) {
        updateNote(userId, destinationId, note)
    }

    suspend fun deleteNote(userId: String, destinationId: String, noteId: String) {
        updateNote(userId, destinationId, Note(noteId, userId,destinationId,"", "deleted"))
    }
}
