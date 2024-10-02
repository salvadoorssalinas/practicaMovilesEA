package pe.edu.upc.practica202401.domain

data class Package (
    val id: String,
    val name: String,
    val description: String,
    val location: String,
    val image: String,
    val typeId: String,
    val siteId: String,
    var isFavorite: Boolean = false
)