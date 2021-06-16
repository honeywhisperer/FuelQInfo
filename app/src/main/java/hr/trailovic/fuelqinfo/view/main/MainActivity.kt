package hr.trailovic.fuelqinfo.view.main

import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import hr.trailovic.fuelqinfo.R
import hr.trailovic.fuelqinfo.databinding.ActivityMainBinding
import hr.trailovic.fuelqinfo.view.add.AddActivity
import hr.trailovic.fuelqinfo.view.main.refueling.RefuelingFragment
import hr.trailovic.fuelqinfo.view.main.stats.StatsFragment
import hr.trailovic.weatherqinfo.base.BaseActivity

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun setup() {
        //todo
        setTitle()
        setViewPager()
        setTabsOnTop()
        setListeners()
    }

    private fun setTitle() {
        binding.layoutTopAppBar.topAppBar.title = "Car Consumption"
    }

    private fun setListeners() {
        binding.layoutMainContent.fabAdd.setOnClickListener {
            val newIntent = AddActivity.createIntent(this)
            startActivity(newIntent)
        }
    }

    private fun setTabsOnTop() {
        TabLayoutMediator(
            binding.layoutMainContent.tabLayout,
        binding.layoutMainContent.pager){tab, position->
            tab.text = when(position){
                0->"Refueling"
                1->"Stats"
                else->"Error"
            }
            tab.icon = when(position){
                0-> AppCompatResources.getDrawable(this, R.drawable.ic_gas)
                else-> AppCompatResources.getDrawable(this, R.drawable.ic_stats)
            }
        }.attach()
    }

    private fun setViewPager() {
        binding.layoutMainContent.pager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return 2
            }

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> RefuelingFragment.newInstance()
                    else -> StatsFragment.newInstance()
                }
            }
        }
    }

}