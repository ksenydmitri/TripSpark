package com.ksenia.tripspark.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksenia.tripspark.domain.model.Destination
import com.ksenia.tripspark.domain.model.Recommendation
import com.ksenia.tripspark.domain.model.User
import com.ksenia.tripspark.domain.usecase.GetRecommendationsUseCase
import com.ksenia.tripspark.domain.usecase.UserUseCases
import com.ksenia.tripspark.domain.usecase.wishlist.WishlistUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MapUiState(
    val wishlistDestinations: List<Destination> = emptyList(),
    val recommendedDestinations: List<Recommendation> = emptyList(),
    val currentUser: User? = null,
    val isLoading: Boolean = false
)

@HiltViewModel
class MapViewModel @Inject constructor(
    private val wishlistUseCases: WishlistUseCases,
    private val getRecommendationsUseCase: GetRecommendationsUseCase,
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            userUseCases.getUser.invoke().collect { user ->
                _uiState.value = _uiState.value.copy(currentUser = user)
                if (user != null) {
                    val wishlistItems = wishlistUseCases.getUserDestinationsUseCase(user.id)
                    val recommendations = getRecommendationsUseCase.invoke()
                    
                    // Filter recommendations to not include items already in wishlist
                    val wishlistIds = wishlistItems.map { it.destinationId }.toSet()
                    val filteredRecommendations = recommendations.filter { it.destination.id !in wishlistIds }
                    
                    // For wishlist, we might need to fetch the full Destination objects if WishlistItem doesn't have location
                    // Based on my previous read, WishlistItem has destinationId and name, but not location.
                    // DestinationRepository has getAllDestinations.
                    
                    val allDestinations = wishlistUseCases.getUserDestinationsUseCase(user.id)
                    // Wait, GetUserDestinationsUseCase returns List<WishlistItem>. 
                    // I need the actual Destination objects for the map (lat/lng).
                    
                    val destinations = recommendations.map { it.destination }
                    val wishlistDestinations = destinations.filter { it.id in wishlistIds }

                    _uiState.value = _uiState.value.copy(
                        wishlistDestinations = wishlistDestinations,
                        recommendedDestinations = filteredRecommendations,
                        isLoading = false
                    )
                }
            }
        }
    }
}
