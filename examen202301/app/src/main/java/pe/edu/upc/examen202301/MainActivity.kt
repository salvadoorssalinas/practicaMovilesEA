package pe.edu.upc.examen202301

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.Room
import pe.edu.upc.examen202301.common.Constants
import pe.edu.upc.examen202301.data.local.AppDatabase
import pe.edu.upc.examen202301.data.remote.AlbumService
import pe.edu.upc.examen202301.data.repository.AlbumRepository
import pe.edu.upc.examen202301.presentation.album_list.AlbumListScreen
import pe.edu.upc.examen202301.presentation.album_list.AlbumListViewModel
import pe.edu.upc.examen202301.presentation.favorite_list.FavoriteAlbumListScreen
import pe.edu.upc.examen202301.presentation.favorite_list.FavoriteAlbumListViewModel
import pe.edu.upc.examen202301.ui.theme.Examen202301Theme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val service = Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AlbumService::class.java)

        val dao = Room
            .databaseBuilder(applicationContext, AppDatabase::class.java, "albums-db")
            .build()
            .getAlbumDao()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Examen202301Theme {
                val items = listOf(
                    ItemTab(
                        "List Album",
                        Icons.Filled.Search
                    ) ,
                    ItemTab(
                        "Favourite Album",
                        Icons.Filled.Favorite
                    )
                )

                val index = remember {
                    mutableStateOf(0)
                }

                Scaffold { paddingValues ->
                    Column (modifier = Modifier.padding(paddingValues)) {
                        LazyRow {
                            itemsIndexed(items) { position, item ->

                                FilterChip(
                                    selected = index.value == position,
                                    leadingIcon = {
                                        Icon(item.icon, item.name)
                                    },
                                    label = {
                                        Text(item.name)
                                    },
                                    onClick = {
                                        index.value = position
                                    }
                                )
                            }
                        }
                        if (index.value == 0) {
                            val albumListViewModel = AlbumListViewModel(AlbumRepository(service, dao))
                            AlbumListScreen(albumListViewModel)
                        } else {
                            val favoriteAlbumListViewModel = FavoriteAlbumListViewModel(AlbumRepository(service, dao))
                            FavoriteAlbumListScreen(favoriteAlbumListViewModel)
                        }
                    }
                }
            }
        }
    }
}

data class ItemTab(
    val name: String,
    val icon: ImageVector
)