package hr.trailovic.fuelqinfo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.trailovic.fuelqinfo.model.DateOption
import hr.trailovic.fuelqinfo.model.FuelRecord
import hr.trailovic.fuelqinfo.repo.FuelRepository
import hr.trailovic.fuelqinfo.toDateString
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val repo: FuelRepository,
) : ViewModel() {

    private val _dayPick = MutableLiveData<Pair<DateOption, Long>>() // date option + date
    val dayPick: LiveData<Pair<DateOption, Long>> = _dayPick

    init {
        setDayPickToday()
    }

    fun setDayPickToday() {
        val calendar = Calendar.getInstance()
        _dayPick.postValue(Pair(DateOption.TODAY, calendar.timeInMillis))
    }

    fun setDayPickAnotherDay(date: Long = 0) {
        val calendar = Calendar.getInstance()
        if (date == 0L) {
            calendar.add(Calendar.DATE, -1)
        } else {
            calendar.timeInMillis = date
        }
        _dayPick.postValue(Pair(DateOption.ANOTHER_DAY, calendar.timeInMillis))
    }

    fun setDateWhenEditing(date: Long) {
        val inputDate = date.toDateString()
        val today = System.currentTimeMillis().toDateString()

        if (inputDate == today) {
            _dayPick.postValue(Pair(DateOption.TODAY, date))
        } else {
            _dayPick.postValue(Pair(DateOption.ANOTHER_DAY, date))
        }
    }

    fun saveFuelRecordData(odometer: Int, liters: Double, comment: String?) {
        viewModelScope.launch {
            val fuelRecord = FuelRecord(
                odometer,
                liters,
                dayPick.value?.second ?: 0,
                comment
            ) //todo: lift check before the function call
            repo.addFuelRecordSuspended(fuelRecord)
        }
    }

    fun updateFuelRecordData(
        fuelRecord: FuelRecord,
        odometer: Int,
        liters: Double,
        comment: String?
    ) {
        viewModelScope.launch {
            val updatedFuelRecord = fuelRecord.copy(
                odometer = odometer,
                liters = liters,
                date = dayPick.value?.second ?: 0, //todo: lift this check before the function call
                comment = comment
            )
            repo.updateFuelRecordSuspended(updatedFuelRecord)
        }
    }

    fun removeAllFuelRecords() {
        viewModelScope.launch {
            repo.removeAllFuelRecords()
        }
    }

}