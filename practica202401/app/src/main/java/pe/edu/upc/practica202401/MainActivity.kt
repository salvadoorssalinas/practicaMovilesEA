package pe.edu.upc.practica202401

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import pe.edu.upc.practica202401.common.Constants
import pe.edu.upc.practica202401.data.local.AppDatabase
import pe.edu.upc.practica202401.data.remote.PackageService
import pe.edu.upc.practica202401.data.repository.PackageRepository
import pe.edu.upc.practica202401.presentation.favorite_packages.FavoritePackagesScreen
import pe.edu.upc.practica202401.presentation.favorite_packages.FavoritePackagesViewModel
import pe.edu.upc.practica202401.presentation.find_package.FindPackageScreen
import pe.edu.upc.practica202401.presentation.find_package.FindPackageViewModel
import pe.edu.upc.practica202401.presentation.home.HomeScreen
import pe.edu.upc.practica202401.presentation.home.HomeViewModel
import pe.edu.upc.practica202401.ui.theme.Practica202401Theme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val service = Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PackageService::class.java)

        val dao = Room
            .databaseBuilder(applicationContext, AppDatabase::class.java, "package-db")
            .build()
            .getPackageDao()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practica202401Theme {
                val navController = rememberNavController()
                val homeViewModel = HomeViewModel(navController)
                val findPackageViewModel = FindPackageViewModel(PackageRepository(service, dao))
                val favoritePackagesViewModel = FavoritePackagesViewModel(PackageRepository(service, dao))
                NavHost(navController = navController, startDestination = "home") {
                    composable(route = "home") {
                        HomeScreen(viewModel = homeViewModel)
                    }
                    composable(route = "find") {
                        FindPackageScreen(viewModel = findPackageViewModel)
                    }
                    composable(route = "favorite") {
                        FavoritePackagesScreen(viewModel = favoritePackagesViewModel)
                    }
                }

            }
        }
    }
}
