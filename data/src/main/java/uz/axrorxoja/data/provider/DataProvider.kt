package uz.axrorxoja.data.provider

class DataProvider {

    private val restProvider by lazy { RestProvider() }

    val repositoryProvider by lazy { DataRepositoryProvider(restProvider) }
}