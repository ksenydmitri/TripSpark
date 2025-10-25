package com.ksenia.tripspark.domain.usecase.wishlist

import com.ksenia.tripspark.domain.repository.UserRepository
import com.ksenia.tripspark.domain.repository.WishlistRepository
import javax.inject.Inject

class RemoveFromWishlistUseCase@Inject constructor(
    private val wishlistRepository: WishlistRepository
) {
    suspend operator fun invoke(userId:String, destinationId: String){
        wishlistRepository.removeFromWishlist(
            userId = userId,
            destinationId = destinationId,)
    }
}