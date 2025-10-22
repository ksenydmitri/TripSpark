package com.ksenia.tripspark.data.datasource.remote

import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.ksenia.tripspark.domain.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.text.get

class UserRemoteDataSource@Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    fun getCurrentUserId(): String? = auth.currentUser?.uid

    suspend fun getUserData(): DocumentSnapshot? {
        val uid = getCurrentUserId() ?: return null
        return try {
            firestore.collection("users").document(uid).get().await()
        } catch (e: Exception) {
            null
        }
    }

    fun createUser(user: User) {
        try{
            firestore.collection("users").document(user.id).set(
                hashMapOf(
                    "name" to user.name,
                    "email" to user.email,
                    "imageId" to user.imageId
                )
            )
        } catch (e: Exception) {
            throw Exception("Ошибка создания записи пользователя")
        }
    }
}