package pe.edu.upc.examen202301.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
data class AlbumEntity (
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "strAlbum")
    val strAlbum: String,
    @ColumnInfo(name = "strArtist")
    val strArtist: String,
    @ColumnInfo(name = "strAlbumThumb")
    val strAlbumThumb: String,
    @ColumnInfo(name = "intYearReleased")
    val intYearReleased: Int,
    @ColumnInfo(name = "intScore")
    val intScore: Float,
    @ColumnInfo(name = "strGenre")
    val strGenre: String,
    @ColumnInfo(name = "strAlbum3DCase")
    val strAlbum3DCase: String
)