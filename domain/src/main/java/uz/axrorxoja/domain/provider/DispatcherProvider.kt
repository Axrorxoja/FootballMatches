package uz.axrorxoja.domain.provider

import kotlinx.coroutines.Dispatchers

class DispatcherProvider : IDispatcherProvider {
    override val IO = Dispatchers.IO
    override val Default = Dispatchers.Default
    override val Main = Dispatchers.Main
}