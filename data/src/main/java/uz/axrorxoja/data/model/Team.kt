package uz.axrorxoja.data.model

internal class Team(
    val id: Long,
    val name: String,
    val coach: Coach,
    val captain: Player,
    val lineup: List<Player>,
    val bench: List<Player>
)