package pe.edu.upc.practica202401.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PackageDao {

    @Insert
    suspend fun insert(packageEntity: PackageEntity)

    @Delete
    suspend fun delete(packageEntity: PackageEntity)

    @Query("select * from packages")
    suspend fun fetchAll(): List<PackageEntity>

    @Query("select * from packages where id like :id")
    suspend fun fetchById(id: String): PackageEntity?

    @Query("select * from packages where siteId like :site")
    suspend fun fetchBySite(site: String): List<PackageEntity>

    @Query("select * from packages where typeId like :type")
    suspend fun fetchByType(type: String): List<PackageEntity>
}