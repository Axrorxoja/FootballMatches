package uz.axrorxoja.data.repository.match

import uz.axrorxoja.data.network.Api
import kotlin.Exception

internal class MatchRepository(
    private val api: Api
) : IMatchRepository {

    override suspend fun matchesByCompetition(
        competitionId: Long,
        dateFrom: String,
        dateTo: String
    ): MatchResult {
        return try {
            val response = api.matches(competitionId, dateFrom, dateTo)
            MatchResult(data = response.matches)
        } catch (e: Exception) {
            MatchResult(error = e)
        }
    }
}