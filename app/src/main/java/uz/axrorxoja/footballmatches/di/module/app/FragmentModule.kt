package uz.axrorxoja.footballmatches.di.module.app

import dagger.Module
import dagger.android.ContributesAndroidInjector
import uz.axrorxoja.footballmatches.di.module.TeamDetailFragmentModule
import uz.axrorxoja.footballmatches.di.scope.FragmentScope
import uz.axrorxoja.footballmatches.presentation.view.team.TeamDetailFragment

@Module
interface FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [TeamDetailFragmentModule::class])
    fun teamDetailFragment(): TeamDetailFragment

}