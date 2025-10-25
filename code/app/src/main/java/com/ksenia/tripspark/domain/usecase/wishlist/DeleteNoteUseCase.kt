package com.ksenia.tripspark.domain.usecase.wishlist

import com.ksenia.tripspark.domain.repository.WishlistRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: WishlistRepository
) {
    suspend operator fun invoke(userId: String, destinationId: String, noteId: String) {
        repository.deleteNote(userId, destinationId, noteId)
    }
}
