package com.ksenia.tripspark.domain.usecase

import javax.inject.Inject

class UserUseCases @Inject constructor(
    val getUser: GetUserUseCase,
    val updateLocalUserUseCase: UpdateLocalUserUseCase,
    val loginUserWithEmailAndPasswordUseCase: LoginUserWithEmailAndPasswordUseCase,
    val registerUserUseCase: RegisterUserUseCase
)