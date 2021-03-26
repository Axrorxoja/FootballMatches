package uz.axrorxoja.footballmatches.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import uz.axrorxoja.data.provider.DataProvider
import uz.axrorxoja.domain.usecase.ILoadMostWinningTeamUseCase
import uz.axrorxoja.domain.usecase.LoadMostWinningTeamUseCase
import uz.axrorxoja.footballmatches.App
import uz.axrorxoja.footballmatches.di.scope.AppScope
import uz.axrorxoja.footballmatches.util.IResourceProvider
import uz.axrorxoja.footballmatches.util.ResourceProvider

@Module
class AppModule {

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
    fun provideRepositoryProvider() = DataProvider()

    @AppScope
    @Provides
    fun provideUseCase(dataProvider: DataProvider): ILoadMostWinningTeamUseCase {
        val provider = dataProvider.repositoryProvider
        return LoadMostWinningTeamUseCase(
            provider.matchRepository,
            provider.competitionRepository,
            provider.teamRepository
        )
    }
}