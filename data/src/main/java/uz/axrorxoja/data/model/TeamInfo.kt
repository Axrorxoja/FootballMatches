package uz.axrorxoja.data.model

class TeamInfo(
    val id: Long,
    val name: String,
    val coach: Coach,
    val captain: Player,
    val lineup: List<Player>,
    val bench: List<Player>
)