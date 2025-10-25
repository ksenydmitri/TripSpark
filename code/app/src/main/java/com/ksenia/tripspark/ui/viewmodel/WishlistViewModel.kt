package com.ksenia.tripspark.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksenia.tripspark.domain.model.Note
import com.ksenia.tripspark.domain.model.User
import com.ksenia.tripspark.domain.model.WishlistItem
import com.ksenia.tripspark.domain.usecase.UserUseCases
import com.ksenia.tripspark.domain.usecase.wishlist.WishlistUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val wishlistUseCases: WishlistUseCases,
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _wishlist = MutableStateFlow<List<WishlistItem>>(emptyList())
    val wishlist: StateFlow<List<WishlistItem>> = _wishlist

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadCurrentUser()
    }

    fun loadCurrentUser() {
        viewModelScope.launch {
            userUseCases.getUser.invoke().collect { user ->
                println("Loaded user: ${user?.id}")
                _currentUser.value = user
                user?.id?.let { loadWishlist(it) }
            }
        }
    }

    fun loadWishlist(userId: String) {
        viewModelScope.launch {
            val items = wishlistUseCases.getUserDestinationsUseCase(userId)
            println("Wishlist loaded: ${items.size} items for user $userId")
            _wishlist.value = items
        }
    }


    fun add(userId: String, item: WishlistItem) {
        viewModelScope.launch {
            wishlistUseCases.addToWishListUseCase(userId, item)
            loadWishlist(userId)
        }
    }

    fun remove(userId: String, destinationId: String) {
        viewModelScope.launch {
            wishlistUseCases.removeFromWishListUseCase(userId, destinationId)
            loadWishlist(userId)
        }
    }

    fun updateNote(userId: String, destinationId: String, note: Note) {
        viewModelScope.launch {
            wishlistUseCases.updateNoteUseCase(userId, destinationId, note)
            loadWishlist(userId)
        }
    }

    fun loadNote(userId: String, destinationId: String): String? {
        return _wishlist.value.find { it.destinationId == destinationId }?.note
    }
}
