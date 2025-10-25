package com.ksenia.tripspark.domain.usecase.wishlist

import com.ksenia.tripspark.domain.model.Destination
import com.ksenia.tripspark.domain.model.Note
import com.ksenia.tripspark.domain.repository.UserRepository
import com.ksenia.tripspark.domain.repository.WishlistRepository
import javax.inject.Inject

class UpdateWishlistItemUseCase@Inject constructor(
    private val wishlistRepository: WishlistRepository
) {
    suspend operator fun invoke(userId: String, destination: Destination, note: Note){
        wishlistRepository.updateNote(userId,destination.id,note)
    }
}