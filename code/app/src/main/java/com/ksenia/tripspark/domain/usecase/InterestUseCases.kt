package com.ksenia.tripspark.domain.usecase

import javax.inject.Inject

data class InterestUseCases@Inject constructor(
    val getInterestsUseCase: GetInterestsUseCase,
    val updateUserInterestsUseCase: UpdateUserInterestsUseCase
)