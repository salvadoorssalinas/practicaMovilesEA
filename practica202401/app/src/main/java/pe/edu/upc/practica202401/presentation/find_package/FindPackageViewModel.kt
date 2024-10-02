package pe.edu.upc.practica202401.presentation.find_package

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pe.edu.upc.practica202401.common.Resource
import pe.edu.upc.practica202401.common.UIState
import pe.edu.upc.practica202401.data.repository.PackageRepository
import pe.edu.upc.practica202401.domain.Package

class FindPackageViewModel(private val repository: PackageRepository): ViewModel() {

    private val _site = mutableStateOf("")
    val site: State<String> get() = _site

    private val _type = mutableStateOf("")
    val type: State<String> get() = _type

    private val _state = mutableStateOf(UIState<List<Package>>())
    val state: State<UIState<List<Package>>> get() = _state

    fun onSiteChanged(site: String) {
        _site.value = site
    }

    fun onTypeChanged(type: String) {
        _type.value = type
    }

    fun searchPackage() {
        _state.value = UIState(isLoading = true)
        viewModelScope.launch {
            try {
                val result = repository.searchPackages(_site.value, _type.value)
                if (result is Resource.Success) {
                    _state.value = UIState(data = result.data)
                } else {
                    _state.value = UIState(message = result.message ?: "An error occurred")
                }
            } catch (e: Exception) {
                _state.value = UIState(message = e.message ?: "An error occurred")
            }
        }
    }

    fun toggleFavorite(pckg: Package){
        pckg.isFavorite = !pckg.isFavorite
        viewModelScope.launch {
            if (pckg.isFavorite) {
                repository.insertPackage(pckg)
            } else {
                repository.deletePackage(pckg)
            }
            val packages = _state.value.data
            _state.value = UIState(data = emptyList())
            _state.value = UIState(data = packages)

        }
    }

}