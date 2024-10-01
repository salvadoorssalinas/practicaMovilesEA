package pe.edu.upc.examen202301.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.examen202301.common.Resource
import pe.edu.upc.examen202301.data.local.AlbumDao
import pe.edu.upc.examen202301.data.local.AlbumEntity
import pe.edu.upc.examen202301.data.remote.AlbumDto
import pe.edu.upc.examen202301.data.remote.AlbumService
import pe.edu.upc.examen202301.data.remote.toAlbum
import pe.edu.upc.examen202301.domain.Album

class AlbumRepository(
    private val albumService: AlbumService,
    private val albumDao: AlbumDao
) {

    private suspend fun isFavorite(id: String): Boolean = withContext(Dispatchers.IO) {
        albumDao.fetchAlbumById(id)?.let {
            return@withContext true
        }
        return@withContext false
    }

    suspend fun searchAll(): Resource<List<Album>> = withContext(Dispatchers.IO) {
        val response = albumService.getAlbums()
        if (response.isSuccessful) {
            response.body()?.albums?.let { albumsDto ->
                val albums = mutableListOf<Album>()
                albumsDto.forEach { albumDto: AlbumDto ->
                    val album = albumDto.toAlbum()
                    album.isFavorite = isFavorite(album.id)
                    albums.add(album)
                }
                return@withContext Resource.Success(data = albums)
            }
            return@withContext Resource.Error(
                message = response.body()?.error ?: "An error occurred"
            )
        }
        return@withContext Resource.Error(message = response.message())
    }

    suspend fun insertAlbum(album: Album) = withContext(Dispatchers.IO) {
        albumDao.insert(AlbumEntity(album.id, album.name, album.artist, album.thumb, album.year, album.score, album.genre, album.case))
    }

    suspend fun deleteAlbum(album: Album) = withContext(Dispatchers.IO) {
        albumDao.delete(AlbumEntity(album.id, album.name, album.artist, album.thumb, album.year, album.score, album.genre, album.case))
    }

    suspend fun getFavorites(): Resource<List<Album>> = withContext(Dispatchers.IO) {
        try {
            val albumsEntity = albumDao.fetchAll()
            val albums = albumsEntity.map { albumEntity: AlbumEntity ->
                Album(
                    id = albumEntity.id,
                    name = albumEntity.strAlbum,
                    artist = albumEntity.strArtist,
                    thumb = albumEntity.strAlbumThumb,
                    year = albumEntity.intYearReleased,
                    score = albumEntity.intScore,
                    genre = albumEntity.strGenre,
                    case = albumEntity.strAlbum3DCase
                )
            }.toList()
            return@withContext Resource.Success(data = albums)

        } catch (e: Exception) {
            return@withContext Resource.Error(message = e.message ?: "An exception occurred")

        }
    }
}