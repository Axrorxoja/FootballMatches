package uz.axrorxoja.footballmatches

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber
import uz.axrorxoja.footballmatches.di.component.DaggerAppComponent

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<App> =
        DaggerAppComponent.factory().create(this)

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}