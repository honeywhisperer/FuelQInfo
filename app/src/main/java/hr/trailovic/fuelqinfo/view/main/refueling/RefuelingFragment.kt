package hr.trailovic.fuelqinfo.view.main.refueling

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import hr.trailovic.fuelqinfo.databinding.FragmentRefuelingBinding
import hr.trailovic.weatherqinfo.base.BaseFragment

class RefuelingFragment : BaseFragment<FragmentRefuelingBinding>() {

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRefuelingBinding {
        return FragmentRefuelingBinding.inflate(inflater, container, false)
    }

    override fun setup() {
        //todo
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