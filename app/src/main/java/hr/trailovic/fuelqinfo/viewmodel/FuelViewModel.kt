package hr.trailovic.fuelqinfo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.trailovic.fuelqinfo.model.FuelRecord
import hr.trailovic.fuelqinfo.repo.FuelRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FuelViewModel @Inject constructor(
    private val repo: FuelRepository
) : ViewModel() {

    private val _fuelRecords = MutableLiveData<List<FuelRecord>>()
    val fuelRecords: LiveData<List<FuelRecord>> = _fuelRecords

    init {
        _xx()
    }

    //test
    fun _xx(){
        viewModelScope.launch {
            repo.getAllFuelRecords().collect {
                _fuelRecords.postValue(it)
            }
        }
    }
}