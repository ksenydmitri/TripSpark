package com.ksenia.tripspark.data.datasource.remote

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.ksenia.tripspark.domain.model.Continent
import com.ksenia.tripspark.domain.model.Interest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class InterestDataSource@Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun getInterests(): List<Interest>{
        val snapshot = firestore.collection("interests").get().await()
        val interests = snapshot.documents.mapNotNull { doc ->
            val id = doc.id
            val name = doc.getString("name")
            val vectorRaw = doc.get("vector") as? List<*> ?: emptyList<Any>()
            val vector = vectorRaw.mapNotNull { it as? Number }.map { it.toFloat() }
            Log.e("GET INTERESTS","${name} ${vector.size}")
            if (name != null)
                Interest(id, name, vector = vector) else null
        }
        return interests
    }

    suspend fun getUserInterests(uid: String): List<Interest> {
        val doc = firestore.collection("users").document(uid).get().await()
        val refs = doc.get("interests") as? List<DocumentReference> ?: emptyList()
        val interests = refs.mapNotNull { ref ->
            val interestDoc = ref.get().await()
            val name = interestDoc.getString("name")
            val id = ref.id
            if (name != null) Interest(id, name, vector = emptyList()) else null
        }
        return interests
    }

    suspend fun updateUserInterests(uid: String, selectedIds: List<String>) {
        val userDoc = firestore.collection("users").document(uid)
        val interestRefs = selectedIds.map { id ->
            firestore.collection("interests").document(id)
        }
        userDoc.update("interests", interestRefs).await()
    }

    suspend fun updateUserContinents(uid: String, selectedIds: List<String>) {
        val userDoc = firestore.collection("users").document(uid)
        val continentRefs = selectedIds.map { id ->
            firestore.collection("continents").document(id)
        }
        userDoc.update("continents", continentRefs).await()
    }

    suspend fun getContinents(): List<Continent>{
        val snapshot = firestore.collection("continents").get().await()
        val continents = snapshot.documents.mapNotNull { doc ->
            Continent(
                id = doc.id,
                name = doc.getString("name"),
                centerLat = doc.getDouble("centerLat"),
                centerLon = doc.getDouble("centerLon"),
                screenYPercent = doc.getDouble("screenYPercent")?.toFloat() ?: 0f,
                screenXPercent = doc.getDouble("screenXPercent")?.toFloat() ?: 0f
            )
        }
        return continents
    }
}