package pe.edu.upc.examen202301.data.remote

import com.google.gson.annotations.SerializedName
import pe.edu.upc.examen202301.domain.Album

data class AlbumDto (
    @SerializedName("idAlbum")
    val id: String,
    @SerializedName("strAlbum")
    val name: String,
    @SerializedName("strArtist")
    val artist: String,
    @SerializedName("strAlbumThumb")
    val thumb: String,
    @SerializedName("intYearReleased")
    val year: Int,
    @SerializedName("intScore")
    val score: Float,
    @SerializedName("strGenre")
    val genre: String?,
    @SerializedName("strAlbum3DCase")
    val case: String?
)

fun AlbumDto.toAlbum(): Album {
    return Album(
        id = id,
        name = name,
        artist = artist,
        thumb = thumb,
        year = year,
        score = score,
        genre = genre?: "",
        case = case ?: ""
    )
}