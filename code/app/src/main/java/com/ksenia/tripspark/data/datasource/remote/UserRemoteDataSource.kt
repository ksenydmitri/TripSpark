package com.ksenia.tripspark.data.datasource.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.text.get

class UserRemoteDataSource@Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    fun getCurrentUserId(): String? = auth.currentUser?.uid

    fun createUserDocumentIfNotExists(onComplete: (Boolean) -> Unit) {
        val uid = getCurrentUserId() ?: return onComplete(false)
        val userRef = firestore.collection("users").document(uid)

        userRef.get().addOnSuccessListener { doc ->
            if (!doc.exists()) {
                val userData = hashMapOf(
                    "email" to auth.currentUser?.email
                )
                userRef.set(userData)
                    .addOnSuccessListener { onComplete(true) }
                    .addOnFailureListener { onComplete(false) }
            } else {
                onComplete(true)
            }
        }.addOnFailureListener {
            onComplete(false)
        }
    }

    suspend fun getUserData(): DocumentSnapshot? {
        val uid = getCurrentUserId() ?: return null
        return try {
            firestore.collection("users").document(uid).get().await()
        } catch (e: Exception) {
            null
        }
    }
}