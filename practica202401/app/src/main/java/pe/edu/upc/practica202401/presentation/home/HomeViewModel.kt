package pe.edu.upc.practica202401.presentation.home

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class HomeViewModel (private val navController: NavController): ViewModel() {

    fun goToFindPackage() {
        navController.navigate("find")
    }

    fun goToFavoritePackages() {
        navController.navigate("favorite")
    }
}