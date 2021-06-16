package hr.trailovic.fuelqinfo.model

data class ConsumptionStatistics(
    val averageConsumption: StatElement<Double>,
    val averageDriveBetweenRefueling: StatElement<Double>,
    val minimalConsumption: StatElement<Double>,
    val maximalDriveBetweenRefueling: StatElement<Double>,
    val numberOfRefueling: StatElement<Int>,
    val totalDistanceDriven: StatElement<Int>,
)

data class StatElement<T>(val highlight: T, val list: List<T>?)