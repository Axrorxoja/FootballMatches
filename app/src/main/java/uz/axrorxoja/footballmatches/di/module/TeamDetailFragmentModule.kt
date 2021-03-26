package uz.axrorxoja.footballmatches.di.module

import dagger.Module
import dagger.Provides
import uz.axrorxoja.data.provider.DataRepositoryProvider
import uz.axrorxoja.domain.usecase.ILoadMostWinningTeamUseCase
import uz.axrorxoja.domain.usecase.LoadMostWinningTeamUseCase
import uz.axrorxoja.footballmatches.di.scope.FragmentScope

@Module
class TeamDetailFragmentModule {
    @Provides
    @FragmentScope
    fun provideUseCase(provider: DataRepositoryProvider): ILoadMostWinningTeamUseCase {
        return LoadMostWinningTeamUseCase(
            provider.matchRepository,
            provider.competitionRepository,
            provider.teamRepository
        )
    }
}