package com.ksenia.tripspark.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksenia.tripspark.domain.model.User
import com.ksenia.tripspark.domain.usecase.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    init {
        viewModelScope.launch {
            userUseCases.updateLocalUserUseCase.invoke()
            userUseCases.getUser()
                .collect { user ->
                    _currentUser.value = user
                }
        }
    }

    fun isUserLoggedIn(): Boolean{
        return currentUser.value != null
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
}