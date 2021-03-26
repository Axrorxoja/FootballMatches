package uz.axrorxoja.footballmatches.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.axrorxoja.footballmatches.di.scope.AppScope
import javax.inject.Inject
import javax.inject.Provider

@AppScope
class ViewModelProviderFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>,
            Provider<ViewModel>>
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        requireNotNull(creator) { "unknown model class $modelClass" }
        return try {
            creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    companion object {
        private const val TAG = "ViewModelProviderFactor"
    }
}