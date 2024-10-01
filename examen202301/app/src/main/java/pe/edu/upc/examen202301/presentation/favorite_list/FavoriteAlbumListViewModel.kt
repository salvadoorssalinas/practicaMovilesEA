package pe.edu.upc.examen202301.presentation.favorite_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pe.edu.upc.examen202301.common.Resource
import pe.edu.upc.examen202301.common.UIState
import pe.edu.upc.examen202301.data.repository.AlbumRepository
import pe.edu.upc.examen202301.domain.Album

class FavoriteAlbumListViewModel(private val repository: AlbumRepository): ViewModel() {
    private val _state = mutableStateOf(UIState<List<Album>>())
    val state: State<UIState<List<Album>>> get() = _state

    init {
        getFavorites()
    }

    private fun getFavorites() {
        _state.value = UIState(isLoading = true)
        viewModelScope.launch {
            val result = repository.getFavorites()
            if (result is Resource.Success) {
                _state.value = UIState(data = result.data)
            } else {
                _state.value = UIState(message = "An error occurred")
            }
        }
    }

    fun toggleFavorite(album: Album) {
        album.isFavorite = !album.isFavorite
        viewModelScope.launch {
            if (album.isFavorite) {
                repository.insertAlbum(album)
            } else {
                repository.deleteAlbum(album)
            }
            val albums = _state.value.data
            _state.value = UIState(data = emptyList())
            _state.value = UIState(data = albums)
        }
    }
}