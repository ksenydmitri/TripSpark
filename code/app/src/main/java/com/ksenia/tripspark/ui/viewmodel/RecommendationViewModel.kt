package com.ksenia.tripspark.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksenia.tripspark.domain.model.Recommendation
import com.ksenia.tripspark.domain.usecase.GetRecommendationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendationViewModel@Inject constructor(
    val getRecommendationsUseCase: GetRecommendationsUseCase
) : ViewModel() {
    private val _recommendations = MutableStateFlow<List<Recommendation>>(
        emptyList())
    val recommendations : StateFlow<List<Recommendation>> = _recommendations

    init {
        getRecommendations()
    }

    fun getRecommendations() {
        viewModelScope.launch {
            val gotRecommendations = getRecommendationsUseCase.invoke()
            _recommendations.value = gotRecommendations
        }
    }

    fun removeTopCard() {
        _recommendations.value = _recommendations.value.drop(1)
    }
}