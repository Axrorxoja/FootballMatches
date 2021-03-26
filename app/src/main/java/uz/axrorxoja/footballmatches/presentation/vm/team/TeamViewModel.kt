package uz.axrorxoja.footballmatches.presentation.vm.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import uz.axrorxoja.domain.global.DomainState
import uz.axrorxoja.domain.usecase.ILoadMostWinningTeamUseCase
import uz.axrorxoja.footballmatches.R
import uz.axrorxoja.footballmatches.di.scope.FragmentScope
import uz.axrorxoja.footballmatches.util.IResourceProvider
import javax.inject.Inject

class TeamViewModel @Inject constructor(
    private val useCase: ILoadMostWinningTeamUseCase,
    private val resourceProvider: IResourceProvider
) : ViewModel(), ITeamViewModel {

    override val screenState = MutableStateFlow(TeamScreenState(default = true))

    init {
        loadData()
    }

    override fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            screenState.value = TeamScreenState(progress = Unit)
            val state = useCase.loadMostWinningTeamMatches()
            handleState(state)
        }
    }

    private fun handleState(state: DomainState) {
        when (state) {
            is DomainState.SuccessTeam -> screenState.value = TeamScreenState(success = state.data)

            is DomainState.NoNetwork -> TeamScreenState(error = resourceProvider.getString(R.string.no_internet))

            else -> TeamScreenState(error = resourceProvider.getString(R.string.unknown_error))
        }
    }
}