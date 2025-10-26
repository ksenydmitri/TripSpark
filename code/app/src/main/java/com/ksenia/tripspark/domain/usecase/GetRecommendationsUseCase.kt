package com.ksenia.tripspark.domain.usecase

import com.ksenia.tripspark.domain.model.Continent
import com.ksenia.tripspark.domain.model.Destination
import com.ksenia.tripspark.domain.model.Recommendation
import com.ksenia.tripspark.domain.repository.DestinationRepository
import com.ksenia.tripspark.domain.repository.InterestRepository
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.sqrt

class GetRecommendationsUseCase @Inject constructor(
    private val destinationRepository: DestinationRepository,
    private val interestRepository: InterestRepository
) {
    suspend operator fun invoke(): List<Recommendation> {
        val destinations = destinationRepository.getAllDestinations()
        val interests = interestRepository.getChosenInterests()
        val continents = interestRepository.getChosenContinents()
        val userVector = averageVector(interests.map { it.vector })

        return destinations.map { dest ->
            val semanticScore = cosineSimilarity(dest.vector, userVector)
            val geoScore = continentProximityScore(dest, continents)
            val relevance = 0.7f * semanticScore + 0.3f * geoScore
            Recommendation(dest, relevance)
        }.sortedByDescending { it.relevance }

    }

    fun continentProximityScore(
        destination: Destination,
        continents: List<Continent>
    ): Float {
        if (continents.isEmpty()) return 0f

        val scores = continents.map { continent ->
            val distance = calculateDistanceKm(
                destination.location.latitude,
                destination.location.longitude,
                continent.centerLat ?: 0.0,
                continent.centerLon ?: 0.0
            )
            locationWeight(distance)
        }

        return scores.maxOrNull() ?: 0f
    }

    fun calculateDistanceKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Float {
        val earthRadius = 6371.0
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = Math.sin(dLat / 2).pow(2.0) +
                Math.cos(Math.toRadians(lat1)) *
                Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2).pow(2.0)
        val c = 2 * Math.atan2(sqrt(a), sqrt(1 - a))
        return (earthRadius * c).toFloat()
    }

    fun locationWeight(distanceKm: Float): Float {
        return when {
            distanceKm < 500f -> 1f
            distanceKm < 1500f -> 0.8f
            distanceKm < 3000f -> 0.6f
            distanceKm < 6000f -> 0.4f
            else -> 0.2f
        }
    }


    fun cosineSimilarity(a: List<Float>, b: List<Float>): Float {
        if (a.size != b.size || a.isEmpty()) return 0f

        val dotProduct = a.zip(b).sumOf { (x, y) -> (x * y).toDouble() }
        val normA = sqrt(a.sumOf { (it * it).toDouble() })
        val normB = sqrt(b.sumOf { (it * it).toDouble() })

        return if (normA == 0.0 || normB == 0.0) 0f else (dotProduct / (normA * normB)).toFloat()
    }

    fun averageVector(vectors: List<List<Float>>): List<Float> {
        if (vectors.isEmpty()) return emptyList()
        val size = vectors.first().size
        val sum = FloatArray(size)
        vectors.forEach { vector ->
            vector.forEachIndexed { i, value -> sum[i] += value }
        }
        return sum.map { it / vectors.size }
    }

}
