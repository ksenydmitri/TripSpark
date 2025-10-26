package com.ksenia.tripspark.data.datasource.remote

import android.location.Location
import com.google.firebase.firestore.FirebaseFirestore
import com.ksenia.tripspark.domain.model.Destination
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DestinationRemoteDataSource@Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun getAllDestinations(): List<Destination>{
        try {
            val snapshot = firestore.collection("destinations").get().await()
            val destinations = snapshot.documents.mapNotNull { doc ->
                val latitude = doc.getDouble("latitude")  ?: 0.0
                val longitude = doc.getDouble("longitude") ?: 0.0
                val vectorRaw = doc.get("vector") as? List<*> ?: emptyList<Any>()
                val vector = vectorRaw.mapNotNull { it as? Number }.map { it.toFloat() }
                Destination(
                    id = doc.id,
                    name = doc.getString("name") ?: "",
                    description = doc.getString("description") ?: "",
                    imageUrl = doc.getString("imageUrl") ?: "",
                    location = Location("firebase").apply {
                        this.latitude = latitude
                        this.longitude = longitude
                    },
                    rating = doc.getDouble("rating") ?: 0.0,
                    vector = vector
                )
            }
            return destinations
        } catch (e: Exception){
            throw e
        }
    }
}