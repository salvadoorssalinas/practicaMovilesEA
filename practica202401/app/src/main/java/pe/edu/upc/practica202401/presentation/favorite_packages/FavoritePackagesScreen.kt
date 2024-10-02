package pe.edu.upc.practica202401.presentation.favorite_packages

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.practica202401.domain.Package

@Composable
fun FavoritePackagesScreen(viewModel: FavoritePackagesViewModel) {

    val state = viewModel.state.value

    LaunchedEffect (Unit) {
        viewModel.getFavoritePackages()
    }

    Scaffold { paddingValues ->
        Column (
            modifier = Modifier.padding(paddingValues)
        ) {
            Text(
                text = "Favorite Packages",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            if (state.isLoading) {
                CircularProgressIndicator()
            } else if (state.message.isNotEmpty()){
                Text(text = state.message)
            } else {
                state.data?.let { packages: List<Package> ->
                    LazyColumn {
                        items(packages) { pckg: Package ->
                            Card (
                                modifier = Modifier.padding(8.dp)
                            ) {
                                FavoritePackageItem (pckg = pckg) {
                                    viewModel.toggleFavorite(pckg)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun FavoritePackageItem(pckg: Package, onFavoritePressed: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            modifier = Modifier.size(80.dp),
            imageModel = { pckg.image }, // loading a network image using an URL.
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
            Text(text = pckg.name, fontWeight = FontWeight.Bold)
            Text(text = pckg.description)
            Text(text = pckg.location)
        }
        IconButton(onClick = {
            onFavoritePressed()
        }) {
            Icon(
                Icons.Filled.Favorite,
                "Favorite",
                tint = if (pckg.isFavorite) Color.Red else Color.Gray
            )
        }
    }
}