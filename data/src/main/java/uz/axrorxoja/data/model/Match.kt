package uz.axrorxoja.data.model

class Match(
    val id: Long,
    val season: Season,
    val utcDate: String,
    val status: String,
    val attendance: Int,
    val matchday: Int,
    val stage: String,
    val group: String,
    val lastUpdated: String,
    val homeTeam: TeamInfo,
    val awayTeam: TeamInfo,
    val score: Score
)
