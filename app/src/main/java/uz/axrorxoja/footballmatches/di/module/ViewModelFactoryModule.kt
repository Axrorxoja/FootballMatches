package uz.axrorxoja.footballmatches.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import uz.axrorxoja.footballmatches.di.scope.ViewModelKey
import uz.axrorxoja.footballmatches.presentation.vm.DaggerViewModelFactory
import uz.axrorxoja.footballmatches.presentation.vm.team.TeamViewModel


@Module
interface ViewModelFactoryModule {
    @Binds
    fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TeamViewModel::class)
    fun bindTeamViewModel(vm: TeamViewModel):ViewModel
}