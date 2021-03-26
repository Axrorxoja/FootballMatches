package uz.axrorxoja.data.provider

import uz.axrorxoja.data.repository.competition.CompetitionRepository
import uz.axrorxoja.data.repository.competition.ICompetitionRepository
import uz.axrorxoja.data.repository.match.IMatchRepository
import uz.axrorxoja.data.repository.match.MatchRepository
import uz.axrorxoja.data.repository.team.ITeamRepository
import uz.axrorxoja.data.repository.team.TeamRepository

class DataRepositoryProvider internal constructor(
    restProvider: RestProvider
) {
    val matchRepository: IMatchRepository by lazy { MatchRepository(restProvider.api) }
    val teamRepository: ITeamRepository by lazy { TeamRepository(restProvider.api) }
    val competitionRepository: ICompetitionRepository by lazy { CompetitionRepository(restProvider.api) }
}