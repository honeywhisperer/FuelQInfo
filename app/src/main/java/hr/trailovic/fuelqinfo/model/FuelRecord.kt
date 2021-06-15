package hr.trailovic.fuelqinfo.model

import java.util.*

data class FuelRecord(
    val odometer: Int,
    val liters: Double,
    val date: Long,
    val comment: String?,
    val id: String = UUID.randomUUID().toString(),
)
