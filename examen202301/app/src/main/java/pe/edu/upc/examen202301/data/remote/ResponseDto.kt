package pe.edu.upc.examen202301.data.remote

import com.google.gson.annotations.SerializedName

class ResponseDto (
    @SerializedName("loved") // name of array in json
    val albums: List<AlbumDto>?,
    val error: String?
)