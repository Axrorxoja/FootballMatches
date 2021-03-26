package uz.axrorxoja.data.model

class Score(
    val winner: ScoreState,
    val fullTime: ScoreTime,
    val halfTime: ScoreTime,
    val extraTime: ScoreTime,
    val penalties: ScoreTime,
)

class ScoreTime(
    val homeTeam: Int?,
    val awayTeam: Int?,
)