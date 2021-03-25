package uz.axrorxoja.data.repository.match

import uz.axrorxoja.data.model.Match
import java.lang.Exception

class MatchResult(
    val data: List<Match>? = null,
    val error: Exception? = null
)