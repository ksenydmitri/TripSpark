package com.ksenia.tripspark.data.datasource.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class UserRemoteDataSource(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
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

    fun getUserData(onResult: (DocumentSnapshot?) -> Unit) {
        val uid = getCurrentUserId() ?: return onResult(null)
        firestore.collection("users").document(uid)
            .get()
            .addOnSuccessListener { onResult(it) }
            .addOnFailureListener { onResult(null) }
    }
}