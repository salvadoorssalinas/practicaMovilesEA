package pe.edu.upc.practica202401.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "packages")
data class PackageEntity (
    @PrimaryKey
    val id: String,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("description")
    val description: String,
    @ColumnInfo("location")
    val location: String,
    @ColumnInfo("image")
    val image: String,
    @ColumnInfo("typeId")
    val typeId: String,
    @ColumnInfo("siteId")
    val siteId: String
)