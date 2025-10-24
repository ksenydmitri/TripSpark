package com.ksenia.tripspark.data.repository

import android.location.Location
import android.util.Log.e
import com.ksenia.tripspark.data.datasource.local.DestinationDao
import com.ksenia.tripspark.data.datasource.remote.DestinationRemoteDataSource
import com.ksenia.tripspark.domain.model.Destination
import com.ksenia.tripspark.domain.repository.DestinationRepository
import javax.inject.Inject

class DestinationRepositoryImpl@Inject constructor(
    private val destinationLocal: DestinationDao,
    private val destinationRemote: DestinationRemoteDataSource
) : DestinationRepository{

    override suspend fun getAllDestinations(): List<Destination> {
        try {
            val destinationsRemote = destinationRemote.getAllDestinations()
            if (!destinationsRemote.isEmpty()){
                return destinationsRemote
            } else {
                return destinationLocal.getAllDestinations().map { destinationEntity ->
                    Destination(
                        id = destinationEntity.uid,
                        name = destinationEntity.name,
                        description = destinationEntity.description,
                        location = Location("firebase").apply {
                            latitude = destinationEntity.latitude
                            longitude = destinationEntity.longitude
                        },
                        imageUrl = destinationEntity.imageUrl
                    )
                }
            }
        } catch (e: Exception){
            throw e
        }
    }
}