package pe.edu.upc.practica202401.data.remote

import com.google.gson.annotations.SerializedName
import pe.edu.upc.practica202401.domain.Package

data class PackageDto (
    @SerializedName("idProducto")
    val id: String,
    @SerializedName("nombre")
    val name: String,
    @SerializedName("descripcion")
    val description: String,
    @SerializedName("ubicacin")
    val location: String,
    @SerializedName("imagen")
    val image: String,
    @SerializedName("idTipo")
    val typeId: String,
    @SerializedName("idSitio")
    val siteId: String
)

fun PackageDto.toPackage(): Package {
    return Package(
        id = id,
        name = name,
        description = description,
        location = location,
        image = image,
        typeId = typeId,
        siteId = siteId
    )
}