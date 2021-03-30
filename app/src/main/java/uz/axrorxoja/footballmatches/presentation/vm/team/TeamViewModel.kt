package uz.axrorxoja.footballmatches.presentation.vm.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import uz.axrorxoja.domain.global.DomainState
import uz.axrorxoja.domain.usecase.ILoadMostWinningTeamUseCase
import uz.axrorxoja.footballmatches.R
import uz.axrorxoja.footballmatches.util.IResourceProvider
import javax.inject.Inject

@HiltViewModel
class TeamViewModel @Inject constructor(
    private val useCase: ILoadMostWinningTeamUseCase,
    private val resourceProvider: IResourceProvider
) : ViewModel(), ITeamViewModel {

    override val screenState = MutableStateFlow(TeamScreenState(default = true))

    override fun loadData() {
        viewModelScope.launch {
            screenState.value = TeamScreenState(progress = Unit)
            val state = useCase.loadMostWinningTeamMatches()
            handleState(state)
        }
    }

    private fun handleState(state: DomainState) {
        Timber.d("handleState $state")
        screenState.value = when (state) {
            is DomainState.SuccessTeam -> TeamScreenState(success = state.data)
            is DomainState.NoNetwork -> TeamScreenState(error = resourceProvider.getString(R.string.no_internet))
            else -> TeamScreenState(error = resourceProvider.getString(R.string.unknown_error))
        }
    }
}