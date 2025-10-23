package com.ksenia.tripspark.data.repository

import android.R.attr.name
import android.util.Log
import com.ksenia.tripspark.data.datasource.local.InterestDao
import com.ksenia.tripspark.data.datasource.remote.InterestDataSource
import com.ksenia.tripspark.data.model.InterestEntity
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
                    name = interestEntity.name,
                    isChosen = interestEntity.isChosen)

            }
        } else {
            val interests = remoteDataSource.getInterests()
            val localInterests = interests.map { interest ->
                Log.e("GET INTERESTS",interest.name)
                InterestEntity(
                    uid = interest.id,
                    name = interest.name,
                    isChosen = interest.isChosen
                )
            }
            localDataSource.insertAll(localInterests)
            return interests
        }
    }

    override suspend fun syncUserInterests(selectedIds: List<String>) {
        val allInterests = localDataSource.getInterests()
        val updated = allInterests.map {
            it.copy(isChosen = selectedIds.contains(it.uid))
        }
        localDataSource.insertAll(updated)
    }

    override suspend fun getChosenInterests(): List<Interest> {
        val interests = localDataSource.getChosenInterests().map { interestEntity ->
            Interest(
                id = interestEntity.uid,
                name = interestEntity.name,
                isChosen = interestEntity.isChosen
            )
        }
        return interests
    }


}