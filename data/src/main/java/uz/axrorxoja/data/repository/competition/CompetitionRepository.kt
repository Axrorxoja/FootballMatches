package uz.axrorxoja.data.repository.competition

import uz.axrorxoja.data.network.Api
import java.lang.Exception

internal class CompetitionRepository(
    private val api: Api
) : ICompetitionRepository {

    override suspend fun competitionById(
        id: Long
    ): CompetitionResult {
        return try {
            val response = api.competitionById(id)
            CompetitionResult(data = response)
        } catch (e: Exception) {
            CompetitionResult(error = e)
        }
    }
}