package com.ksenia.tripspark.domain.usecase.wishlist

import javax.inject.Inject

data class WishlistUseCases@Inject constructor(
    val getUserDestinationsUseCase: GetUserDestinationsUseCase,
    val addToWishListUseCase: AddToWishlistUseCase,
    val removeFromWishListUseCase: RemoveFromWishlistUseCase,
    val addNoteToDestinationUseCase: AddNoteToDestinationUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val getNoteForDestinationUseCase: GetNoteForDestinationUseCase,
    val updateNoteUseCase: UpdateNoteUseCase
)