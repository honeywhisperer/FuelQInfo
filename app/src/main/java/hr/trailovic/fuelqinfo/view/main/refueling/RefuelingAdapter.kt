package hr.trailovic.fuelqinfo.view.main.refueling

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.trailovic.fuelqinfo.databinding.ItemFuelRecordBinding
import hr.trailovic.fuelqinfo.model.FuelRecord
import hr.trailovic.fuelqinfo.oneDecimal
import hr.trailovic.fuelqinfo.toDateString

class RefuelingAdapter(private val listener: OnItemFuelRecordInteraction) : RecyclerView.Adapter<RefuelingAdapter.FuelRecordViewHolder>() {

    private val fuelList = mutableListOf<FuelRecord>()

    fun setItems(list: List<FuelRecord>){
        with(fuelList){
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FuelRecordViewHolder {
        val itemFuelRecordBinding = ItemFuelRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FuelRecordViewHolder(itemFuelRecordBinding)
    }

    override fun onBindViewHolder(holder: FuelRecordViewHolder, position: Int) {
        holder.bind(fuelList[position])
    }

    override fun getItemCount(): Int = fuelList.size

    inner class FuelRecordViewHolder(private val itemFuelRecordBinding: ItemFuelRecordBinding) :
        RecyclerView.ViewHolder(itemFuelRecordBinding.root) {
        init {
            itemFuelRecordBinding.root.setOnClickListener() {
                listener.onClick(fuelList[layoutPosition])
            }
        }

        fun bind(fuelRecord: FuelRecord) {
            with(itemFuelRecordBinding){
                tvOdometer.text = fuelRecord.odometer.toString()
                tvLiters.text = fuelRecord.liters.oneDecimal()
                tvDate.text = fuelRecord.date.toDateString()
                tvComment.text = fuelRecord.comment ?: ""
            }
        }
    }
}