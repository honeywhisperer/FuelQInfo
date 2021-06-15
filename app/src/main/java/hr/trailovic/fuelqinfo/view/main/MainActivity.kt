package hr.trailovic.fuelqinfo.view.main

import hr.trailovic.fuelqinfo.databinding.ActivityMainBinding
import hr.trailovic.weatherqinfo.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun setup() {
        //todo
    }

}