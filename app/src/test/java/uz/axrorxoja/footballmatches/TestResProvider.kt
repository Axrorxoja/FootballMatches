package uz.axrorxoja.footballmatches

import uz.axrorxoja.footballmatches.util.IResourceProvider

class TestResProvider:IResourceProvider {
    override fun getString(id: Int) = "some test"
}