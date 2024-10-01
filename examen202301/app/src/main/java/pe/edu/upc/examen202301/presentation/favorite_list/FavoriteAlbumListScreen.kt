package pe.edu.upc.examen202301.presentation.favorite_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.examen202301.domain.Album
import pe.edu.upc.examen202301.presentation.album_list.AlbumItem

@Composable
fun FavoriteAlbumListScreen(viewModel: FavoriteAlbumListViewModel) {

    val state = viewModel.state.value

    Scaffold { paddingValues ->
        Column (
            modifier = Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else if (state.message.isNotEmpty()) {
                Text(text = state.message)
            } else {
                state.data?.let { albums: List<Album> ->
                    LazyColumn {
                        items(albums) { album: Album ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                            ) {
                                FavoriteAlbumItem(album)
                            }
                        }
                    }
                }


            }

        }
    }
}

@Composable
fun FavoriteAlbumItem(album: Album) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            modifier = Modifier.size(120.dp),
            imageModel = { album.case },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        )
        Column(
            modifier = Modifier
                .padding(8.dp)
                .weight(3f)
        ) {
            Text("Name: ${album.name}")
            Text("Genre: ${album.genre}")
        }

    }
}