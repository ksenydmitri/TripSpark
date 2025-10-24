package com.ksenia.tripspark.data.datasource.remote

import android.R.attr.description
import android.R.attr.name
import android.location.Location
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.getField
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
                val locationMap = doc.get("location") as? Map<*, *>
                val latitude = locationMap?.get("latitude") as? Double ?: 0.0
                val longitude = locationMap?.get("longitude") as? Double ?: 0.0
                Destination(
                    id = doc.id,
                    name = doc.getString("name") ?: "",
                    description = doc.getString("description") ?: "",
                    imageUrl = doc.getString("imageUrl") ?: "",
                    location = Location("firebase").apply {
                        this.latitude = latitude
                        this.longitude = longitude
                    }
                )
            }
            return destinations
        } catch (e: Exception){
            throw e
        }
    }
}