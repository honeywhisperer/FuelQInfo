package hr.trailovic.fuelqinfo.view.main.refueling

import hr.trailovic.fuelqinfo.model.FuelRecord

interface OnItemFuelRecordInteraction {
    fun onClick(fuelRecord: FuelRecord)
}