package uz.axrorxoja.domain.global

import uz.axrorxoja.data.model.Team

sealed class DomainState {
    object NoNetwork : DomainState()
    object UnKnownError : DomainState()
    class SuccessTeam(val data: Team) : DomainState()

}
