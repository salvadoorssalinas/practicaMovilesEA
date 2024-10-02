package pe.edu.upc.practica202401.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    Scaffold { paddingValues ->  
        Column (modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .padding(16.dp)
        )
        {
            Text("Bienvenido a AppTurismo",
                modifier = Modifier.padding(bottom = 16.dp).align(Alignment.CenterHorizontally),
            )
        }

        LazyRow (
            modifier = Modifier.fillMaxWidth()
        )
        {
            item {
                Card(
                    modifier = Modifier.padding(top = 100.dp),
                    onClick = { viewModel.goToFindPackage() }
                ) {
                    Column {
                        Text("Find Packages",
                            modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally),
                        )
                    }

                }
            }
            item {
                Card(
                    modifier = Modifier.padding(top = 100.dp),
                    onClick = { viewModel.goToFavoritePackages() }
                ) {
                    Text("Favorite Packages",
                        modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally),
                    )
                }
            }

        }
    }

}