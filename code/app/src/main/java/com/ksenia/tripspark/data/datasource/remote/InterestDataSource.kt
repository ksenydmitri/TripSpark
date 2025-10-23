package com.ksenia.tripspark.data.datasource.remote

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
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
            Log.e("GET INTERESTS",name ?: "")
            if (name != null)
                Interest(id, name, isChosen = false) else null
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
            if (name != null) Interest(id, name, isChosen = true) else null
        }
        return interests
    }
}