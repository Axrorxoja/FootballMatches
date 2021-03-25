package uz.axrorxoja.data.model

internal class MatchResponse(
    val count: Int = 0,
    val competition: Competition,
    val matches: List<Match>
)