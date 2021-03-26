package uz.axrorxoja.footballmatches.di.component

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import uz.axrorxoja.footballmatches.App
import uz.axrorxoja.footballmatches.di.module.ActivityModule
import uz.axrorxoja.footballmatches.di.module.AppModule
import uz.axrorxoja.footballmatches.di.module.ViewModelFactoryModule
import uz.axrorxoja.footballmatches.di.scope.AppScope

@AppScope
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
        ViewModelFactoryModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Factory
    interface Factory : AndroidInjector.Factory<App>
}