package hr.trailovic.fuelqinfo.database

import androidx.room.*
import hr.trailovic.fuelqinfo.model.FuelRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface FuelRecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFuelRecordSuspended(fuelRecord: FuelRecord)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateFuelRecordSuspended(fuelRecord: FuelRecord)

    @Delete
    suspend fun removeFuelRecordSuspended(fuelRecord: FuelRecord)

    @Query("DELETE FROM FuelRecord")
    suspend fun removeAllFuelRecords()

    // todo: implement get with Kotlin Flow

    @Query("SELECT * FROM FuelRecord")
    fun getAllFuelRecords(): Flow<List<FuelRecord>>
}