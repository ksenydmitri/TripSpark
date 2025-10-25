package com.ksenia.tripspark.domain.usecase.wishlist

import com.ksenia.tripspark.domain.model.WishlistItem
import com.ksenia.tripspark.domain.repository.UserRepository
import com.ksenia.tripspark.domain.repository.WishlistRepository
import javax.inject.Inject

class AddToWishlistUseCase@Inject constructor(
    private val wishlistRepository: WishlistRepository
) {
    suspend operator fun invoke(userId: String, wishlistItem: WishlistItem){
        wishlistRepository.addToWishlist(userId,wishlistItem)
    }
}