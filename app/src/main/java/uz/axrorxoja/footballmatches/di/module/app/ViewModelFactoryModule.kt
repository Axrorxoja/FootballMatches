package uz.axrorxoja.footballmatches.di.module.app

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import uz.axrorxoja.footballmatches.di.scope.ViewModelKey
import uz.axrorxoja.footballmatches.presentation.vm.ViewModelProviderFactory
import uz.axrorxoja.footballmatches.presentation.vm.team.TeamViewModel


@Module
abstract class ViewModelFactoryModule {
    @Binds
    @IntoMap
    @ViewModelKey(TeamViewModel::class)
    abstract fun bindTeamViewModel(viewModelProviderFactory: ViewModelProviderFactory?): ViewModelProvider.Factory
}