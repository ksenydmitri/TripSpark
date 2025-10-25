package com.ksenia.tripspark.domain.usecase

import javax.inject.Inject

data class UserUseCases @Inject constructor(
    val getUser: GetUserUseCase,
    val updateLocalUserUseCase: UpdateLocalUserUseCase,
    val loginUserWithEmailAndPasswordUseCase: LoginUserWithEmailAndPasswordUseCase,
    val registerUserUseCase: RegisterUserUseCase,
    val logoutUseCase: LogoutUseCase,
    val uploadImageUseCase: UploadImageUseCase,
    val updateUserAvatarUseCase: UpdateUserAvatarUseCase
)