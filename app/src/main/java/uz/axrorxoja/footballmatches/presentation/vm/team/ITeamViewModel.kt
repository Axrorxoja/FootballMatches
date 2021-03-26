package uz.axrorxoja.footballmatches.presentation.vm.team

import kotlinx.coroutines.flow.StateFlow

interface ITeamViewModel {

    val screenState: StateFlow<TeamScreenState>

    fun loadData()

}