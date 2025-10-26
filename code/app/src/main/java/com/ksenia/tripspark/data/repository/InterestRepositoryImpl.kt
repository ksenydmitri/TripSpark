package com.ksenia.tripspark.data.repository

import android.util.Log
import com.ksenia.tripspark.data.datasource.local.InterestDao
import com.ksenia.tripspark.data.datasource.remote.InterestDataSource
import com.ksenia.tripspark.data.model.ContinentEntity
import com.ksenia.tripspark.data.model.InterestEntity
import com.ksenia.tripspark.domain.model.Continent
import com.ksenia.tripspark.domain.model.Interest
import com.ksenia.tripspark.domain.repository.InterestRepository
import javax.inject.Inject

class InterestRepositoryImpl@Inject constructor(
    private val remoteDataSource: InterestDataSource,
    private val localDataSource: InterestDao
): InterestRepository {

    override suspend fun getInterests(): List<Interest> {
        val interests = localDataSource.getInterests()
        if (!interests.isEmpty()){
            return interests.map { interestEntity ->
                Log.e("GET INTERESTS",interestEntity.name)
                Interest(
                    id = interestEntity.uid,
                    name = interestEntity.name, vector = emptyList())

            }
        } else {
            val interests = remoteDataSource.getInterests()
            val localInterests = interests.map { interest ->
                Log.e("GET INTERESTS",interest.name)
                InterestEntity(
                    uid = interest.id,
                    name = interest.name
                )
            }
            localDataSource.insertAllInterest(localInterests)
            return interests
        }
    }

    override suspend fun syncUserInterests(userId: String, selectedIds: List<String>) {
        val allInterests = localDataSource.getInterests()
        remoteDataSource.updateUserInterests(userId, selectedIds)
        val updated = allInterests.map {
            it.copy(isChosen = selectedIds.contains(it.uid))
        }
        localDataSource.insertAllInterest(updated)
    }

    override suspend fun getChosenInterests(): List<Interest> {
        val interests = localDataSource.getChosenInterests().map { interestEntity ->
            Interest(
                id = interestEntity.uid,
                name = interestEntity.name, vector = emptyList()
            )
        }
        return interests
    }

    override suspend fun getContinents(): List<Continent> {
        val local = localDataSource.getContinents()
        return if (local.isNotEmpty()) {
            local.map { entity ->
                Continent(
                    id = entity.uid,
                    name = entity.name,
                    centerLat = entity.centerLat,
                    centerLon = entity.centerLon,
                    screenXPercent = entity.screenXPercent,
                    screenYPercent = entity.screenYPercent
                )
            }
        } else {
            val remote = remoteDataSource.getContinents()
            val entities = remote.map { continent ->
                ContinentEntity(
                    uid = continent.id,
                    name = continent.name,
                    centerLat = continent.centerLat,
                    centerLon = continent.centerLon,
                    screenXPercent = continent.screenXPercent,
                    screenYPercent = continent.screenYPercent,
                    isChosen = false
                )
            }
            localDataSource.insertAllContinents(entities)
            remote
        }
    }

    override suspend fun syncUserContinents(userId: String, selectedIds: List<String>) {
        val allContinents = localDataSource.getContinents()
        remoteDataSource.updateUserContinents(userId, selectedIds)
        val updated = allContinents.map {
            it.copy(isChosen = selectedIds.contains(it.uid))
        }
        localDataSource.insertAllContinents(updated)
    }

    override suspend fun getChosenContinents(): List<Continent> {
        return localDataSource.getChosenContinents().map {
            Continent(
                id = it.uid,
                name = it.name,
                centerLat = it.centerLat,
                centerLon = it.centerLon,
                screenXPercent = it.screenXPercent,
                screenYPercent = it.screenYPercent
            )
        }
    }


}