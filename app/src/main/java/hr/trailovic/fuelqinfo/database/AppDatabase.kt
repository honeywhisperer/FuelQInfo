package hr.trailovic.fuelqinfo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import hr.trailovic.fuelqinfo.model.FuelRecord

@Database(entities = [FuelRecord::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun fuelRecordDao(): FuelRecordDao
}