package pe.edu.upc.practica202401.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.practica202401.common.Resource
import pe.edu.upc.practica202401.data.local.PackageDao
import pe.edu.upc.practica202401.data.local.PackageEntity
import pe.edu.upc.practica202401.data.remote.PackageDto
import pe.edu.upc.practica202401.data.remote.PackageService
import pe.edu.upc.practica202401.data.remote.toPackage
import pe.edu.upc.practica202401.domain.Package

class PackageRepository(
    private val packageService: PackageService,
    private val packageDao: PackageDao
) {

    private suspend fun isFavorite(id: String): Boolean = withContext(Dispatchers.IO) {
        packageDao.fetchById(id)?.let {
            return@withContext true
        }
        return@withContext false
    }

    suspend fun searchPackages(site: String, type: String): Resource<List<Package>> = withContext(Dispatchers.IO) {
        val response = packageService.searchPackage(site, type)
        if (response.isSuccessful) {

            response.body()?.let { packagesDto ->
                val packages = mutableListOf<Package>()
                packagesDto.forEach { packageDto: PackageDto ->
                    val pckg = packageDto.toPackage()
                    pckg.isFavorite = isFavorite(pckg.id)
                    packages.add(pckg)
                }
                return@withContext Resource.Success(data = packages)
            }
            return@withContext Resource.Error(
                message = "An error occurred"
            )
        }
        return@withContext Resource.Error(message = response.message())
    }

    suspend fun insertPackage(pckg: Package) = withContext(Dispatchers.IO) {
        packageDao.insert(PackageEntity(pckg.id, pckg.name, pckg.description, pckg.location, pckg.image, pckg.typeId, pckg.siteId))
    }

    suspend fun deletePackage(pckg: Package) = withContext(Dispatchers.IO) {
        packageDao.delete(PackageEntity(pckg.id, pckg.name, pckg.description, pckg.location, pckg.image, pckg.typeId, pckg.siteId))
    }

    suspend fun getFavoritePackages(): Resource<List<Package>> = withContext(Dispatchers.IO) {
        try {
            val packagesEntity = packageDao.fetchAll()
            val packages = packagesEntity.map { packageEntity ->
                Package(
                    packageEntity.id,
                    packageEntity.name,
                    packageEntity.description,
                    packageEntity.location,
                    packageEntity.image,
                    packageEntity.typeId,
                    packageEntity.siteId,
                    isFavorite = true
                )
            }.toList()
            return@withContext Resource.Success(data = packages)
        } catch (e: Exception) {
            return@withContext Resource.Error(message = e.message ?: "An error occurred")
        }
    }

}