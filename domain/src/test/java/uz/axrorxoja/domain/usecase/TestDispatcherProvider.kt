package uz.axrorxoja.domain.usecase

import kotlinx.coroutines.Dispatchers
import uz.axrorxoja.domain.provider.IDispatcherProvider

class TestDispatcherProvider : IDispatcherProvider {
    override val IO = Dispatchers.Unconfined
    override val Default = Dispatchers.Unconfined
    override val Main = Dispatchers.Unconfined
}