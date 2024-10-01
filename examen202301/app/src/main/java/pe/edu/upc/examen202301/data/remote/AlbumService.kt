package pe.edu.upc.examen202301.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface AlbumService {

    @Headers("Accept: application/json")
    @GET("mostloved.php?format=album")
    suspend fun getAlbums(): Response<ResponseDto>
}