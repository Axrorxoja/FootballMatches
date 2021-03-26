package uz.axrorxoja.data.model

class MatchResponse(
    val count: Int = 0,
    val competition: Competition,
    val matches: List<Match>
)