package uz.axrorxoja.footballmatches.presentation.vm.team

import uz.axrorxoja.data.model.Team

data class TeamScreenState(
    val success: Team? = null,
    val progress: Unit? = null,
    val default: Boolean = false,
    val error: String? = null
)