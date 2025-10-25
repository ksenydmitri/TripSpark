package com.ksenia.tripspark.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksenia.tripspark.domain.model.Recommendation
import com.ksenia.tripspark.domain.model.User
import com.ksenia.tripspark.domain.model.WishlistItem
import com.ksenia.tripspark.domain.usecase.GetRecommendationsUseCase
import com.ksenia.tripspark.domain.usecase.GetUserUseCase
import com.ksenia.tripspark.domain.usecase.wishlist.WishlistUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendationViewModel @Inject constructor(
    private val getRecommendationsUseCase: GetRecommendationsUseCase,
    private val wishlistUseCases: WishlistUseCases,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private var allRecommendations: List<Recommendation> = emptyList()
    private var currentIndex = 0
    private val batchSize = 5

    private val _recommendations = MutableStateFlow<List<Recommendation>>(emptyList())
    val recommendations: StateFlow<List<Recommendation>> = _recommendations

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    init {
        viewModelScope.launch {
            getUserUseCase.invoke().collect {
                user -> _currentUser.value = user
            }
            allRecommendations = getRecommendationsUseCase.invoke()
            println("ViewModel loaded ${allRecommendations.size} recommendations")
            loadNextBatch()
        }
    }

    fun loadNextBatch() {
        val nextBatch = allRecommendations.drop(currentIndex).take(batchSize)
        _recommendations.value = nextBatch
        currentIndex += batchSize
    }

    fun removeTopCard() {
        _recommendations.value = _recommendations.value.drop(1)

        if (_recommendations.value.isEmpty() && currentIndex < allRecommendations.size) {
            loadNextBatch()
        }
    }

    fun addToWishlist(userId: String, recommendation: Recommendation) {
        val item = WishlistItem(
            destinationId = recommendation.destination.id,
            name = recommendation.destination.name,
            note = "",
            addedAt = System.currentTimeMillis()
        )
        viewModelScope.launch {
            wishlistUseCases.addToWishListUseCase(userId, item)
            Log.e("ADD TO WISHLIST", "add item ${item.name} for user ${userId}")
        }
    }
}
