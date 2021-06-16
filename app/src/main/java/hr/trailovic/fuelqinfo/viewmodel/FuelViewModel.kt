package hr.trailovic.fuelqinfo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.trailovic.fuelqinfo.model.ConsumptionStatistics
import hr.trailovic.fuelqinfo.model.FuelRecord
import hr.trailovic.fuelqinfo.model.StatElement
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

    private val _consumptionStatistics = MutableLiveData<ConsumptionStatistics>()
    val consumptionStatistics: LiveData<ConsumptionStatistics> = _consumptionStatistics

    init {
        getFuelRecords()
        calculateStatistics()
    }

    private fun getFuelRecords() {
        viewModelScope.launch {
            repo.getAllFuelRecords().collect {
                _fuelRecords.postValue(it)
            }
        }
    }

    private fun calculateStatistics() {
        viewModelScope.launch {
            repo.getAllFuelRecords().collect { fuelList ->
                if (fuelList.isNotEmpty()){
                    val totalLiters = fuelList.map { it.liters }.sum()
                    val totalDistance = fuelList.last().odometer - fuelList.first().odometer

                    val consumptionList = mutableListOf<Double>()
                    val drivenList = mutableListOf<Double>()

                    for (i in 1..fuelList.lastIndex) {
                        consumptionList.add(fuelList[i].liters / (fuelList[i].odometer - fuelList[i - 1].odometer) * 100)
                        drivenList.add((fuelList[i].odometer - fuelList[i-1].odometer).toDouble())
                    }

                    val consumptionStatistics = ConsumptionStatistics(
                        StatElement(100*totalLiters/totalDistance, consumptionList),
                        StatElement(drivenList.average(), drivenList),
                        StatElement(consumptionList.minOf { it }, consumptionList),
                        StatElement(drivenList.maxOf { it }, drivenList),
                        StatElement(fuelList.size, null),
                        StatElement(totalDistance, null)
                    )
                    _consumptionStatistics.postValue(consumptionStatistics)
                }

            }
        }
    }
}