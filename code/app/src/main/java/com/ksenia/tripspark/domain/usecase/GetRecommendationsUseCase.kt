package com.ksenia.tripspark.domain.usecase

import android.location.Location
import com.ksenia.tripspark.domain.model.Destination
import com.ksenia.tripspark.domain.model.Interest
import com.ksenia.tripspark.domain.model.Recommendation
import com.ksenia.tripspark.domain.repository.InterestRepository
import com.ksenia.tripspark.domain.repository.DestinationRepository
import javax.inject.Inject

class GetRecommendationsUseCase @Inject constructor(
    private val destinationRepository: DestinationRepository,
    private val interestRepository: InterestRepository
) {
    suspend operator fun invoke(): List<Recommendation> {
        val destinations = destinationRepository.getAllDestinations()
        val interests = interestRepository.getChosenInterests()

        return destinations.map { dest ->
            val relevance = calculateRelevance(dest, interests)
            Recommendation(dest, relevance)
        }.sortedByDescending { it.relevance }
    }

    fun calculateRelevance(
        destination: Destination,
        interests: List<Interest>
    ): Float {
        var score = 0f
        if (interests.any { destination.description.contains(it.name, ignoreCase = true) }) {
            score += 0.5f
        }
        if (destination.description.contains("autumn", ignoreCase = true)) {
            score += 0.2f
        }

        return score
    }

}
