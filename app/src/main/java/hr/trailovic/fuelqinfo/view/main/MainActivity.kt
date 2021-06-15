package hr.trailovic.fuelqinfo.view.main

import dagger.hilt.android.AndroidEntryPoint
import hr.trailovic.fuelqinfo.databinding.ActivityMainBinding
import hr.trailovic.weatherqinfo.base.BaseActivity

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun setup() {
        //todo
    }

}