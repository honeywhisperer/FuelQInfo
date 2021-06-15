package hr.trailovic.fuelqinfo.repo

import hr.trailovic.fuelqinfo.database.AppDatabase
import hr.trailovic.fuelqinfo.model.FuelRecord
import javax.inject.Inject

class FuelRepository @Inject constructor(
    appDatabase: AppDatabase
) {
    private val fuelRecordDao = appDatabase.fuelRecordDao()

    suspend fun addFuelRecordSuspended(fuelRecord: FuelRecord){
        fuelRecordDao.addFuelRecordSuspended(fuelRecord)
    }

    suspend fun updateFuelRecordSuspended(fuelRecord: FuelRecord){
        fuelRecordDao.updateFuelRecordSuspended(fuelRecord)
    }

    suspend fun removeFuelRecordSuspended(fuelRecord: FuelRecord){
        fuelRecordDao.removeFuelRecordSuspended(fuelRecord)
    }

    suspend fun removeAllFuelRecords(){
        fuelRecordDao.removeAllFuelRecords()
    }

}