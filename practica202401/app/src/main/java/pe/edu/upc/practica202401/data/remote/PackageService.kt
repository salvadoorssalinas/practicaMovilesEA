package pe.edu.upc.practica202401.data.remote

import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.GET
import retrofit2.http.Query

interface PackageService {

    @Headers("Content-Type: application/json")
    @GET("productossitiotipo.php")
    suspend fun searchPackage(
        @Query("sitio") idSitio: String,
        @Query("tipo") idTipo: String
    ): Response<List<PackageDto>>
}