package uz.axrorxoja.data.repository.team

import uz.axrorxoja.data.network.Api

internal class TeamRepository(
    private val api: Api
) : ITeamRepository {

    override suspend fun teamById(id: Long): TeamResult {
        return try {
            val response = api.teamById(id)
            TeamResult(data = response)
        } catch (e: Exception) {
            TeamResult(error = e)
        }
    }
}