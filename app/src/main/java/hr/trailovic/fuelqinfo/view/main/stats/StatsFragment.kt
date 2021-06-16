package hr.trailovic.fuelqinfo.view.main.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import hr.trailovic.fuelqinfo.databinding.FragmentStatsBinding
import hr.trailovic.fuelqinfo.viewmodel.FuelViewModel
import hr.trailovic.weatherqinfo.base.BaseFragment


class StatsFragment : BaseFragment<FragmentStatsBinding>() {

    private val statsAdapter = StatsAdapter()
    private val viewModel: FuelViewModel by activityViewModels()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStatsBinding {
        return FragmentStatsBinding.inflate(inflater, container, false)
    }

    override fun setup() {
        setStatsList()
        bind()
    }

    private fun bind() {
        viewModel.consumptionStatistics.observe(requireActivity()){
            statsAdapter.setItems(it)
        }
    }

    private fun setStatsList() {
        binding.rvStats.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvStats.adapter = statsAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            StatsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}