package pe.edu.upc.examen202301.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AlbumDao {

    @Insert
    suspend fun insert(album: AlbumEntity)

    @Delete
    suspend fun delete(album: AlbumEntity)

    @Query("select * from albums where id = :id")
    suspend fun fetchAlbumById(id: String): AlbumEntity?

    @Query("select * from albums")
    suspend fun fetchAll(): List<AlbumEntity>
}