package com.ksenia.tripspark.domain.usecase.interests

import javax.inject.Inject

data class InterestUseCases @Inject constructor(
    val getInterestsUseCase: GetInterestsUseCase,
    val getChosenInterestsUseCase: GetChosenInterestsUseCase,
    val updateUserInterestsUseCase: UpdateUserInterestsUseCase,
    val getContinentsUseCase: GetContinentsUseCase,
    val getChosenContinentsUseCase: GetChosenContinentsUseCase,
    val updateUserContinentsUseCase: UpdateUserContinentsUseCase
)
