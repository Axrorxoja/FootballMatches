package uz.axrorxoja.data.repository.team

interface ITeamRepository {
    suspend fun teamById(id: Long): TeamResult
}