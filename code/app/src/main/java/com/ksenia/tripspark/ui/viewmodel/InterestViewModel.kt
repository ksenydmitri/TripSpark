package com.ksenia.tripspark.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksenia.tripspark.domain.model.Interest
import com.ksenia.tripspark.domain.model.User
import com.ksenia.tripspark.domain.usecase.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterestViewModel @Inject constructor(
    private val userUseCases: UserUseCases
): ViewModel() {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _interests = MutableStateFlow<List<Interest>>(emptyList())
    val interests: StateFlow<List<Interest>> = _interests.asStateFlow()

    init {
        observeCurrentUser()
    }

    private fun observeCurrentUser(){
        viewModelScope.launch {
            userUseCases.getUser.invoke().collect {
                user -> _currentUser.value = user
            }
        }
    }
}