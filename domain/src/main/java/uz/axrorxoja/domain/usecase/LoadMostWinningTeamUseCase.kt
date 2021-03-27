package uz.axrorxoja.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.axrorxoja.data.model.Competition
import uz.axrorxoja.data.model.Match
import uz.axrorxoja.data.model.ScoreState
import uz.axrorxoja.data.repository.competition.ICompetitionRepository
import uz.axrorxoja.data.repository.match.IMatchRepository
import uz.axrorxoja.data.repository.team.ITeamRepository
import uz.axrorxoja.domain.global.Const
import uz.axrorxoja.domain.global.DomainState
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class LoadMostWinningTeamUseCase(
    private val matchRepository: IMatchRepository,
    private val competitionRepository: ICompetitionRepository,
    private val teamRepository: ITeamRepository
) : ILoadMostWinningTeamUseCase {
    private val dateFormatter =
        SimpleDateFormat(Const.DEFAULT_SERVER_DATE_FORMAT, Locale.getDefault())

    override suspend fun loadMostWinningTeamMatches(): DomainState = withContext(Dispatchers.IO) {
        val competitionResult = competitionRepository
            .competitionById(Const.DEFAULT_COMPETITION_ID)
        val competitionData = competitionResult.data
        val competitionError = competitionResult.error
        return@withContext if (competitionData != null) {
            val pairDate = getSuitableDate(competitionData)
            val matchResult = matchRepository
                .matchesByCompetition(Const.DEFAULT_COMPETITION_ID, pairDate.first, pairDate.second)

            val matchData = matchResult.data
            val matchError = matchResult.error
            if (matchData != null) {
                val teamId = computeMostWinningTeam(matchData)
                val teamResult = teamRepository.teamById(teamId)
                if (teamResult.data != null) {
                    DomainState.SuccessTeam(teamResult.data!!)
                } else {
                    teamResult.error.createStateByException()
                }
            } else {
                matchError.createStateByException()
            }
        } else {
            competitionError.createStateByException()
        }
    }

    private fun getSuitableDate(competitionData: Competition): Pair<String, String> {
        val season = competitionData.currentSeason
        val endDate = dateFormatter.parse(season.endDate)
        return if (endDate != null) {
            if (endDate.before(Date())) {
                val dateFrom = dateFormatter.format(monthAgoDate())
                val dateTo = dateFormatter.format(Date())
                dateFrom to dateTo
            } else {
                season.startDate to season.endDate
            }
        } else {
            val dateFrom = dateFormatter.format(monthAgoDate())
            val dateTo = dateFormatter.format(Date())
            dateFrom to dateTo
        }

    }

    private fun computeMostWinningTeam(matches: List<Match>): Long {
        val map = hashMapOf<Long, Int>()
        for (match in matches) {
            when (match.score.winner) {
                ScoreState.HOME_TEAM -> {
                    map[match.homeTeam.id] = map[match.homeTeam.id] ?: 0 + 1
                }
                ScoreState.AWAY_TEAM -> {
                    map[match.awayTeam.id] = map[match.awayTeam.id] ?: 0 + 1
                }
                else -> {
                    map[match.homeTeam.id] = map[match.homeTeam.id] ?: 0 + 1
                    map[match.awayTeam.id] = map[match.awayTeam.id] ?: 0 + 1
                }
            }
        }
        var max = 0
        var id = 0L
        for (entry in map) {
            if (max < entry.value) {
                max = entry.value
                id = entry.key
            }
        }
        return id
    }

    private fun Exception?.createStateByException(): DomainState {
        return if (this != null && this is IOException) {
            DomainState.NoNetwork
        } else DomainState.UnKnownError
    }

    private fun monthAgoDate(): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)
        return calendar.time
    }
}