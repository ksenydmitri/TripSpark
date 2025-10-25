package com.ksenia.tripspark.ui.viewmodel

import android.R.attr.data
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.ksenia.tripspark.domain.model.Destination
import com.ksenia.tripspark.domain.model.User
import com.ksenia.tripspark.domain.usecase.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private val _avatarUrl = MutableStateFlow<String?>(null)
    val avatarUrl: StateFlow<String?> = _avatarUrl

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

    fun uploadUserAvatar(context: Context, uri: Uri) {
        viewModelScope.launch {
            val userId = currentUser.value?.id ?: return@launch
            val (bytes, contentType) = prepareImage(context, uri)
            val fileName = "avatar_$userId.jpg"

            try {
                val avatarUrl = withContext(Dispatchers.IO) {
                    userUseCases.uploadImageUseCase(fileName, bytes)
                }
                userUseCases.updateUserAvatarUseCase(userId)
                _currentUser.value = currentUser.value?.copy(imageId = avatarUrl)
            } catch (e: Exception) {
                Log.e("UploadAvatar", "Ошибка загрузки аватарки", e)
                _errorMessage.value = e.message
            }
        }
    }


    private fun prepareImage(context: Context, uri: Uri): Pair<ByteArray, String> {
        val bytes = context.contentResolver.openInputStream(uri)?.readBytes()
            ?: throw IllegalArgumentException("Can't read image")
        val contentType = context.contentResolver.getType(uri) ?: "image/jpeg"
        return bytes to contentType
    }

    private fun getUserAvatar(){
        viewModelScope.launch {
            userUseCases.updateLocalUserUseCase()
            userUseCases.getUser.invoke().collect { user ->
                _avatarUrl.value = user?.imageId
            }
        }
    }
}