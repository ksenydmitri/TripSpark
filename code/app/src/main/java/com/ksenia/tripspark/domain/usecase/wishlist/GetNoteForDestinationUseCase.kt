package com.ksenia.tripspark.domain.usecase.wishlist

import com.ksenia.tripspark.domain.model.Note
import com.ksenia.tripspark.domain.repository.WishlistRepository
import javax.inject.Inject

class GetNoteForDestinationUseCase @Inject constructor(
    private val repository: WishlistRepository
) {
    suspend operator fun invoke(userId: String, destinationId: String): Note {
        return repository.getNoteForDestination(userId, destinationId)
    }
}