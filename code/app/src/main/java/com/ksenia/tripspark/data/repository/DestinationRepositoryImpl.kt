package com.ksenia.tripspark.data.repository

import android.location.Location
import com.ksenia.tripspark.data.datasource.Converters
import com.ksenia.tripspark.data.datasource.local.DestinationDao
import com.ksenia.tripspark.data.datasource.remote.DestinationRemoteDataSource
import com.ksenia.tripspark.data.model.DestinationEntity
import com.ksenia.tripspark.domain.model.Destination
import com.ksenia.tripspark.domain.repository.DestinationRepository
import javax.inject.Inject

class DestinationRepositoryImpl@Inject constructor(
    private val destinationLocal: DestinationDao,
    private val destinationRemote: DestinationRemoteDataSource,
    private val converters: Converters
) : DestinationRepository{

    override suspend fun getAllDestinations(): List<Destination> {
        return try {
            val destinationsRemote = destinationRemote.getAllDestinations()
            if (destinationsRemote.isNotEmpty()) {
                destinationLocal.insertAll(destinationsRemote.map { destination ->
                    DestinationEntity(
                        uid = destination.id,
                        name = destination.name,
                        description = destination.description,
                        imageUrl = destination.imageUrl,
                        latitude = destination.location.latitude,
                        longitude = destination.location.longitude,
                        rating = destination.rating,
                        vector = converters.fromVector(destination.vector)
                    )
                })
            }
            destinationLocal.getAllDestinations().map { entity ->
                Destination(
                    id = entity.uid,
                    name = entity.name,
                    description = entity.description,
                    location = Location("local").apply {
                        latitude = entity.latitude
                        longitude = entity.longitude
                    },
                    imageUrl = entity.imageUrl,
                    rating = entity.rating,
                    vector = converters.toVector(entity.vector)
                )
            }
        } catch (e: Exception) {
            destinationLocal.getAllDestinations().map { entity ->
                Destination(
                    id = entity.uid,
                    name = entity.name,
                    description = entity.description,
                    location = Location("local").apply {
                        latitude = entity.latitude
                        longitude = entity.longitude
                    },
                    imageUrl = entity.imageUrl,
                    rating = entity.rating,
                    vector = converters.toVector(entity.vector)
                )
            }
        }
    }

}