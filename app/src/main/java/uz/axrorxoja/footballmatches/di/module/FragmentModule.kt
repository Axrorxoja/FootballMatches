package uz.axrorxoja.footballmatches.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import uz.axrorxoja.footballmatches.di.scope.FragmentScope
import uz.axrorxoja.footballmatches.presentation.view.team.TeamDetailFragment

@Module
interface FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    fun teamDetailFragment(): TeamDetailFragment

}