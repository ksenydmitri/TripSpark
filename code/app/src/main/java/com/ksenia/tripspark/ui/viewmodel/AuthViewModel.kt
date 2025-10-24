package com.ksenia.tripspark.ui.viewmodel

import android.R.attr.data
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.ksenia.tripspark.domain.model.Destination
import com.ksenia.tripspark.domain.model.User
import com.ksenia.tripspark.domain.usecase.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel@Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        getCurrentUser()
    }

    fun getCurrentUser(){
        viewModelScope.launch {
            userUseCases.getUser.invoke().collect { user ->
                _currentUser.value = user
                _isLoading.value = true
            }
        }
    }

    fun isUserLoggedIn(): Boolean {
        val user = currentUser.value
        return user != null && user.id.isNotBlank()
    }

    fun authUserWithEmailAndPassword(email: String, password: String){
        viewModelScope.launch{
            try {
                userUseCases.loginUserWithEmailAndPasswordUseCase(email,password)
            } catch (e: Exception){
                _errorMessage.value = e.message
            }
        }
    }

    fun registerUser(email: String, password: String,name: String){
        viewModelScope.launch{
            try {
                userUseCases.registerUserUseCase(email,password,name)
            } catch (e: Exception){
                _errorMessage.value = e.message
            }
        }
    }

    fun logout(){
        viewModelScope.launch {
            userUseCases.logoutUseCase.invoke()
        }
    }
}