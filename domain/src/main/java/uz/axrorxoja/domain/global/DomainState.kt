package uz.axrorxoja.domain.global

sealed class DomainState {
    class Success<T>(data: T) : DomainState()
    object NoNetwork : DomainState()
    object UnKnownError : DomainState()
}
