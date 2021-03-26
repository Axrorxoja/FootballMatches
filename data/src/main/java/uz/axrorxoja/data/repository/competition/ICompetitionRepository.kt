package uz.axrorxoja.data.repository.competition

interface ICompetitionRepository {
    suspend fun competitionById(id: Long): CompetitionResult
}