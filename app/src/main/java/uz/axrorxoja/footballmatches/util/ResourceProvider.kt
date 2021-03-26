package uz.axrorxoja.footballmatches.util

import android.content.Context

class ResourceProvider(
    context: Context
) : IResourceProvider {
    private val resource = context.resources

    override fun getString(id: Int): String = resource.getString(id)
}