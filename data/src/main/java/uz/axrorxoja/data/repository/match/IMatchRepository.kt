package uz.axrorxoja.data.repository.match

interface IMatchRepository {
    suspend fun matchesByCompetition(
        competitionId:Long,
        dateFrom: String,
        dateTo: String
    ): MatchResult
}