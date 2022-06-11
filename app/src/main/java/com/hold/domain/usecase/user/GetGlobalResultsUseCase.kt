package com.hold.domain.usecase.user

import com.hold.data.repository.GameRepository


class GetGlobalResultsUseCase(
    private val gameRepository: GameRepository,
) {

    suspend operator fun invoke() = gameRepository.getGlobalResults()
}