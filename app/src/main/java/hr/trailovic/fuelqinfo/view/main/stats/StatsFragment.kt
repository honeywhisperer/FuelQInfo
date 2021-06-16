package hr.trailovic.fuelqinfo.view.main.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import hr.trailovic.fuelqinfo.databinding.FragmentStatsBinding
import hr.trailovic.weatherqinfo.base.BaseFragment


class StatsFragment : BaseFragment<FragmentStatsBinding>() {

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStatsBinding {
        return FragmentStatsBinding.inflate(inflater, container, false)
    }

    override fun setup() {
        //todo
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