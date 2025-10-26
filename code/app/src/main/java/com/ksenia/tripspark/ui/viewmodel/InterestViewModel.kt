package com.ksenia.tripspark.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksenia.tripspark.domain.model.User
import com.ksenia.tripspark.domain.usecase.UserUseCases
import com.ksenia.tripspark.domain.usecase.interests.InterestUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterestViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val interestUseCases: InterestUseCases
) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _interests = MutableStateFlow<List<SelectableInterest>>(emptyList())
    val interests: StateFlow<List<SelectableInterest>> = _interests.asStateFlow()

    private val _continents = MutableStateFlow<List<SelectableContinent>>(emptyList())
    val continents: StateFlow<List<SelectableContinent>> = _continents.asStateFlow()

    init {
        viewModelScope.launch {
            _isLoading.value = true
            observeCurrentUser()
            loadInterests()
            loadContinents()
            _isLoading.value = false
        }
    }

    private suspend fun observeCurrentUser() {
        userUseCases.updateLocalUserUseCase.invoke()
        val user = userUseCases.getUser.invoke().firstOrNull()
        _currentUser.value = user
    }

    private fun loadInterests() {
        viewModelScope.launch {
            val raw = interestUseCases.getInterestsUseCase.invoke()
            val chosen = interestUseCases.getChosenInterestsUseCase.invoke()
            _interests.value = raw.map {
                SelectableInterest(
                    id = it.id,
                    name = it.name,
                    vector = it.vector,
                    isChosen = chosen.contains(it.id)
                )
            }
        }
    }

    private fun loadContinents() {
        viewModelScope.launch {
            val raw = interestUseCases.getContinentsUseCase.invoke()
            val chosen = interestUseCases.getChosenContinentsUseCase.invoke()
            _continents.value = raw.map {
                SelectableContinent(
                    id = it.id,
                    name = it.name,
                    centerLat = it.centerLat,
                    centerLon = it.centerLon,
                    screenXPercent = it.screenXPercent,
                    screenYPercent = it.screenYPercent,
                    isSelected = chosen.contains(it.id)
                )
            }
        }
    }

    fun toggleInterest(id: String) {
        _interests.value = _interests.value.map {
            if (it.id == id) it.copy(isChosen = !it.isChosen) else it
        }
        saveSelectedInterests()
    }

    fun toggleContinent(id: String) {
        _continents.value = _continents.value.map {
            if (it.id == id) it.copy(isSelected = !it.isSelected) else it
        }
        saveSelectedContinents()
    }

    fun saveSelectedInterests() {
        val selected = _interests.value.filter { it.isChosen }
        val userId = currentUser.value?.id ?: return

        viewModelScope.launch {
            interestUseCases.updateUserInterestsUseCase
                .invoke(userId = userId,
                    selectedIds = selected.map {
                        interest -> interest.id }
                )
        }
    }

    fun saveSelectedContinents() {
        val selectedIds = _continents.value.filter { it.isSelected }.map { it.id }
        val userId = currentUser.value?.id ?: return

        viewModelScope.launch {
            interestUseCases.updateUserContinentsUseCase.invoke(userId, selectedIds)
        }
    }

    data class SelectableInterest(
        val id: String,
        val name: String,
        val vector: List<Float>,
        val isChosen: Boolean = false
    )

    data class SelectableContinent(
        val id: String,
        val name: String?,
        val centerLat: Double?,
        val centerLon: Double?,
        val screenXPercent: Float,
        val screenYPercent: Float,
        val isSelected: Boolean = false
    )
}