package uz.axrorxoja.footballmatches.di.module.app

import android.content.Context
import dagger.Module
import dagger.Provides
import uz.axrorxoja.data.provider.DataProvider
import uz.axrorxoja.footballmatches.App
import uz.axrorxoja.footballmatches.di.scope.AppScope
import uz.axrorxoja.footballmatches.util.IResourceProvider
import uz.axrorxoja.footballmatches.util.ResourceProvider

@Module
class AppModule {
    private val dataProvider = DataProvider()

    @AppScope
    @Provides
    fun provideContext(application: App): Context {
        return application.applicationContext
    }

    @AppScope
    @Provides
    fun provideResourceProvider(context: Context): IResourceProvider {
        return ResourceProvider(context)
    }

    @AppScope
    @Provides
    fun provideRepositoryProvider() = dataProvider.repositoryProvider
}