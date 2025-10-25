package com.ksenia.tripspark.di.modules

import com.ksenia.tripspark.domain.repository.AuthRepository
import com.ksenia.tripspark.domain.repository.DestinationRepository
import com.ksenia.tripspark.domain.repository.ImageRepository
import com.ksenia.tripspark.domain.repository.InterestRepository
import com.ksenia.tripspark.domain.repository.UserRepository
import com.ksenia.tripspark.domain.repository.WishlistRepository
import com.ksenia.tripspark.domain.usecase.GetInterestsUseCase
import com.ksenia.tripspark.domain.usecase.wishlist.GetUserDestinationsUseCase
import com.ksenia.tripspark.domain.usecase.GetUserUseCase
import com.ksenia.tripspark.domain.usecase.LoginUserWithEmailAndPasswordUseCase
import com.ksenia.tripspark.domain.usecase.RegisterUserUseCase
import com.ksenia.tripspark.domain.usecase.UpdateLocalUserUseCase
import com.ksenia.tripspark.domain.usecase.UpdateUserAvatarUseCase
import com.ksenia.tripspark.domain.usecase.UpdateUserInterestsUseCase
import com.ksenia.tripspark.domain.usecase.UploadImageUseCase
import com.ksenia.tripspark.domain.usecase.wishlist.AddNoteToDestinationUseCase
import com.ksenia.tripspark.domain.usecase.wishlist.AddToWishlistUseCase
import com.ksenia.tripspark.domain.usecase.wishlist.DeleteNoteUseCase
import com.ksenia.tripspark.domain.usecase.wishlist.GetNoteForDestinationUseCase
import com.ksenia.tripspark.domain.usecase.wishlist.RemoveFromWishlistUseCase
import com.ksenia.tripspark.domain.usecase.wishlist.UpdateNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetUserUseCase(
        userRepository: UserRepository
    ): GetUserUseCase{
        return GetUserUseCase(userRepository)
    }

    @Provides
    fun provideLoginUserWithEmailAndPasswordUseCase(
        userRepository: UserRepository,
        authRepository: AuthRepository
    ): LoginUserWithEmailAndPasswordUseCase{
        return LoginUserWithEmailAndPasswordUseCase(
            authRepository,userRepository
        )
    }

    @Provides
    fun provideRegisterUseCase(
        userRepository: UserRepository,
        authRepository: AuthRepository
    ): RegisterUserUseCase {
        return RegisterUserUseCase(
            authRepository,userRepository
        )
    }

    @Provides
    fun provideUpdateLocalUserUseCase(
        userRepository: UserRepository
    ): UpdateLocalUserUseCase{
        return UpdateLocalUserUseCase(userRepository)
    }

    @Provides
    fun provideGetInterestsUseCase(
        interestRepository: InterestRepository
    ): GetInterestsUseCase{
        return GetInterestsUseCase(interestRepository)
    }

    @Provides
    fun provideUpdateUserInterestsUseCase(
        userRepository: UserRepository,
        interestRepository: InterestRepository
    ): UpdateUserInterestsUseCase{
        return UpdateUserInterestsUseCase(
            userRepository,interestRepository
        )
    }

    @Provides
    fun provideUpdateUserAvatarUseCase(
        userRepository: UserRepository,
        imageRepository: ImageRepository
    ): UpdateUserAvatarUseCase {
        return UpdateUserAvatarUseCase(userRepository,imageRepository)
    }

    @Provides
    fun provideUploadImageUseCase(
        imageRepository: ImageRepository
    ): UploadImageUseCase {
        return UploadImageUseCase(imageRepository)
    }
    
    @Provides
    fun provideGetUserDestinationsUseCase(
        wishlistRepository: WishlistRepository
    ): GetUserDestinationsUseCase {
        return GetUserDestinationsUseCase(wishlistRepository)
    }

    @Provides
    fun provideAddToWishlistUseCase(
        wishlistRepository: WishlistRepository
    ): AddToWishlistUseCase {
        return AddToWishlistUseCase(wishlistRepository)
    }

    @Provides
    fun provideRemoveFromWishlistUseCase(
        wishlistRepository: WishlistRepository
    ): RemoveFromWishlistUseCase {
        return RemoveFromWishlistUseCase(wishlistRepository)
    }

    @Provides
    fun provideAddNoteToDestinationUseCase(
        wishlistRepository: WishlistRepository
    ): AddNoteToDestinationUseCase {
        return AddNoteToDestinationUseCase(wishlistRepository)
    }

    @Provides
    fun provideDeleteNoteUseCase(
        wishlistRepository: WishlistRepository
    ): DeleteNoteUseCase {
        return DeleteNoteUseCase(wishlistRepository)
    }

    @Provides
    fun provideGetNotesForDestinationUseCase(
        wishlistRepository: WishlistRepository
    ): GetNoteForDestinationUseCase {
        return GetNoteForDestinationUseCase(wishlistRepository)
    }

    @Provides
    fun provideUpdateNoteUseCase(
        wishlistRepository: WishlistRepository
    ): UpdateNoteUseCase {
        return UpdateNoteUseCase(wishlistRepository)
    }

}