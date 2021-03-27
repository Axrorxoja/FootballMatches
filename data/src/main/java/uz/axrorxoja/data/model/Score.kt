package uz.axrorxoja.data.model

class Score(
    val winner: ScoreState,
    val fullTime: ScoreTime? = null,
    val halfTime: ScoreTime? = null,
    val extraTime: ScoreTime? = null,
    val penalties: ScoreTime? = null,
)

class ScoreTime(
    val homeTeam: Int?,
    val awayTeam: Int?,
)