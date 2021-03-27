package uz.axrorxoja.domain.provider

import kotlinx.coroutines.CoroutineDispatcher

interface IDispatcherProvider {
    val IO: CoroutineDispatcher
    val Default: CoroutineDispatcher
    val Main: CoroutineDispatcher
}