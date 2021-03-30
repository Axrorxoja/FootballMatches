package uz.axrorxoja.footballmatches.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.axrorxoja.data.provider.DataProvider
import uz.axrorxoja.domain.provider.DispatcherProvider
import uz.axrorxoja.domain.provider.IDispatcherProvider
import uz.axrorxoja.domain.usecase.ILoadMostWinningTeamUseCase
import uz.axrorxoja.domain.usecase.LoadMostWinningTeamUseCase
import uz.axrorxoja.footballmatches.App
import uz.axrorxoja.footballmatches.util.IResourceProvider
import uz.axrorxoja.footballmatches.util.ResourceProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideResourceProvider(
        @ApplicationContext context: Context
    ): IResourceProvider {
        return ResourceProvider(context)
    }

    @Singleton
    @Provides
    fun provideRepositoryProvider() = DataProvider()

    @Singleton
    @Provides
    fun provideDispatcher(): IDispatcherProvider = DispatcherProvider()

    @Singleton
    @Provides
    fun provideUseCase(
        dataProvider: DataProvider,
        dispatcherProvider: IDispatcherProvider
    ): ILoadMostWinningTeamUseCase {
        val provider = dataProvider.repositoryProvider
        return LoadMostWinningTeamUseCase(
            provider.matchRepository,
            provider.competitionRepository,
            provider.teamRepository,
            dispatcherProvider
        )
    }
}