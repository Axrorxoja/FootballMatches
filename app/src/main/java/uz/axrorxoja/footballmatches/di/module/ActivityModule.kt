package uz.axrorxoja.footballmatches.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import uz.axrorxoja.footballmatches.di.scope.ActivityScope
import uz.axrorxoja.footballmatches.AppActivity

@Module
interface ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun appActivity(): AppActivity

}