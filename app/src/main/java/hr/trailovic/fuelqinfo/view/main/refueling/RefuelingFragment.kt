package hr.trailovic.fuelqinfo.view.main.refueling

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import hr.trailovic.fuelqinfo.databinding.FragmentRefuelingBinding
import hr.trailovic.fuelqinfo.model.FuelRecord
import hr.trailovic.fuelqinfo.viewmodel.FuelViewModel
import hr.trailovic.weatherqinfo.base.BaseFragment

@AndroidEntryPoint
class RefuelingFragment : BaseFragment<FragmentRefuelingBinding>() {

    private val refuelingAdapter = RefuelingAdapter(object : OnItemFuelRecordInteraction{
        override fun onClick(fuelRecord: FuelRecord) {
            //todo: display menu with options: edit, remove
        }
    })
    private val viewModel: FuelViewModel by viewModels()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRefuelingBinding {
        return FragmentRefuelingBinding.inflate(inflater, container, false)
    }

    override fun setup() {
        //todo
        setRefuelingList()
        bind()
    }

    private fun bind() {
        viewModel.fuelRecords.observe(this){
            refuelingAdapter.setItems(it)
        }
    }

    private fun setRefuelingList() {
        with(binding.rvRefuelingList){
            layoutManager = LinearLayoutManager(requireContext())
            adapter = refuelingAdapter
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RefuelingFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}