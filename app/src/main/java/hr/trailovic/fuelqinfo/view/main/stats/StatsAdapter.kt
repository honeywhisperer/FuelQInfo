package hr.trailovic.fuelqinfo.view.main.stats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.trailovic.fuelqinfo.databinding.ItemStatisticsBinding
import hr.trailovic.fuelqinfo.model.ConsumptionStatistics
import hr.trailovic.fuelqinfo.model.StatElement
import hr.trailovic.fuelqinfo.oneDecimal
import org.eazegraph.lib.charts.ValueLineChart
import org.eazegraph.lib.models.ValueLinePoint
import org.eazegraph.lib.models.ValueLineSeries

class StatsAdapter : RecyclerView.Adapter<StatsAdapter.StatsViewHolder>() {

    private val statsList = mutableListOf<StatElement<Number>>()

    private val description = listOf(
        "Average Consumption",
        "Avg Drive Between Refueling",
        "Minimal Consumption",
        "Maximal Drive Between Refueling",
        "Number of Refueling",
        "Total Distance Driven"
    )

    fun setItems(consumptionStatistics: ConsumptionStatistics) {
        with(statsList) {
            clear()
            add(0, consumptionStatistics.averageConsumption)
            add(1, consumptionStatistics.averageDriveBetweenRefueling)
            add(2, consumptionStatistics.minimalConsumption)
            add(3, consumptionStatistics.maximalDriveBetweenRefueling)
            add(4, consumptionStatistics.numberOfRefueling)
            add(5, consumptionStatistics.totalDistanceDriven)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsViewHolder {
        val itemStatisticsBinding =
            ItemStatisticsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StatsViewHolder(itemStatisticsBinding)
    }

    override fun onBindViewHolder(holder: StatsViewHolder, position: Int) {
        holder.bind(statsList[position])
    }

    override fun getItemCount(): Int = statsList.size

    inner class StatsViewHolder(private val itemStatisticsBinding: ItemStatisticsBinding) :
        RecyclerView.ViewHolder(itemStatisticsBinding.root) {
        init {

        }

        fun bind(statElement: StatElement<Number>) {
            with(itemStatisticsBinding) {
                tvDescription.text = description[layoutPosition]
                if (statElement.highlight::class == Double::class) {
                    tvHighlightValue.text = statElement.highlight.toDouble().oneDecimal()
                } else {
                    tvHighlightValue.text = statElement.highlight.toString()
                }
            }
            statElement.list?.let { list ->
                itemStatisticsBinding.cubiclinechart.visibility = View.VISIBLE

                itemStatisticsBinding.cubiclinechart.isUseCubic = list.size <= 10

                val lineChart: ValueLineChart = itemStatisticsBinding.cubiclinechart
                val series = ValueLineSeries()
                series.color = 0xFF56B7F1.toInt()
                list.forEach {
                    series.addPoint(ValueLinePoint(it.toFloat()))
                }
                lineChart.addSeries(series)
                lineChart.startAnimation()
            } ?: run { itemStatisticsBinding.cubiclinechart.visibility = View.GONE }
        }
    }
}