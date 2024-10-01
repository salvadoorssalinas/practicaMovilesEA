package pe.edu.upc.examen202301.domain

data class Album (
    val id: String,
    val name: String,
    val artist: String,
    val thumb: String,
    val year: Int,
    val score: Float,
    val genre: String,
    val case: String,
    var isFavorite: Boolean = false
)