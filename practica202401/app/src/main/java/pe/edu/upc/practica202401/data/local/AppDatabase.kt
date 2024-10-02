package pe.edu.upc.practica202401.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PackageEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getPackageDao(): PackageDao
}