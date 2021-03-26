package uz.axrorxoja.data.model

class Competition(
    val id: Long,
    val area: Area,
    val name: String,
    val code: String,
    val plan: String,
    val currentSeason: Season,
    val lastUpdated: String
)