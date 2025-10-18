package com.ksenia.tripspark.domain.usecase

import javax.inject.Inject

class UserUseCases @Inject constructor(
    val getUser: GetUserUseCase
) {

}