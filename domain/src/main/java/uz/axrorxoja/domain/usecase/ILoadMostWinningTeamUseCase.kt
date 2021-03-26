package uz.axrorxoja.domain.usecase

import uz.axrorxoja.domain.global.DomainState

interface ILoadMostWinningTeamUseCase {
    suspend fun loadMostWinningTeamMatches(): DomainState
}