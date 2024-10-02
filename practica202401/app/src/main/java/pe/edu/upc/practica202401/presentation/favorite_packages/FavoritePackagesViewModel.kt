package pe.edu.upc.practica202401.presentation.favorite_packages

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pe.edu.upc.practica202401.common.Resource
import pe.edu.upc.practica202401.common.UIState
import pe.edu.upc.practica202401.data.repository.PackageRepository
import pe.edu.upc.practica202401.domain.Package

class FavoritePackagesViewModel(private val repository: PackageRepository): ViewModel() {
    private val _state = mutableStateOf(UIState<List<Package>>())
    val state: State<UIState<List<Package>>> get() = _state

    fun getFavoritePackages() {
        _state.value = UIState(isLoading = true)
        viewModelScope.launch {
            val result = repository.getFavoritePackages()
            if (result is Resource.Success) {
                _state.value = UIState(data = result.data)
            } else {
                _state.value = UIState(message = "An error occurred")
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