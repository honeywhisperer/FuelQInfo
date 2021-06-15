package hr.trailovic.fuelqinfo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.trailovic.fuelqinfo.model.DateOption
import java.util.*

class AddViewModel : ViewModel() {
    private val _dayPick = MutableLiveData<Pair<DateOption, Long>>() // date option + date
    val payPick: LiveData<Pair<DateOption, Long>> = _dayPick

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
}