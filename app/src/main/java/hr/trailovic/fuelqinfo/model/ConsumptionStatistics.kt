package hr.trailovic.fuelqinfo.model

data class ConsumptionStatistics(
    val averageConsumption: StatElement<Number>, //Double
    val averageDriveBetweenRefueling: StatElement<Number>, //Double
    val minimalConsumption: StatElement<Number>, //Double
    val maximalDriveBetweenRefueling: StatElement<Number>, //Double
    val numberOfRefueling: StatElement<Number>, //Int
    val totalDistanceDriven: StatElement<Number>, //Int
)

data class StatElement<T : Number>(val highlight: T, val list: List<T>? = null)

//data class StatElement(val highlight: Number, val list: List<Number>? = null)