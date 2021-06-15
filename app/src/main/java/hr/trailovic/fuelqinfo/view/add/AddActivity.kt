package hr.trailovic.fuelqinfo.view.add

import android.view.View
import androidx.activity.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import hr.trailovic.fuelqinfo.databinding.ActivityAddBinding
import hr.trailovic.fuelqinfo.flashMessage
import hr.trailovic.fuelqinfo.model.DateOption
import hr.trailovic.fuelqinfo.toDateString
import hr.trailovic.fuelqinfo.viewmodel.AddViewModel
import hr.trailovic.weatherqinfo.base.BaseActivity

private const val TAG = "AddActivity:::"

class AddActivity : BaseActivity<ActivityAddBinding>() {

    private val viewModel: AddViewModel by viewModels()
    private lateinit var datePicker: MaterialDatePicker<Long>


    override fun getBinding(): ActivityAddBinding {
        return ActivityAddBinding.inflate(layoutInflater)
    }

    override fun setup() {
        //todo
        setDatePicker()
        setView()
        setListeners()
    }

    private fun setDatePicker() {

        datePicker = MaterialDatePicker
            .Builder
            .datePicker()
            .setTitleText("Select date")
            .setSelection(viewModel.payPick.value?.second)
            .build()
    }

    private fun setView() {
        viewModel.payPick.observe(this) { dateOptionAndDate ->
            when (dateOptionAndDate.first) {
                DateOption.TODAY -> {
                    binding.tilDate.visibility = View.GONE
                }
                DateOption.ANOTHER_DAY -> {
                    binding.tilDate.visibility = View.VISIBLE
                    binding.tilDate.editText?.setText(dateOptionAndDate.second.toDateString())
                }
            }
        }
    }

    private fun setListeners() {
        binding.btnSave.setOnClickListener {
            val isOdometerInputOk = isInputPositiveNumber(binding.tilOdometerStatus)
            val isFuelInputOk = isInputPositiveNumber(binding.tilLiters)
            if (isOdometerInputOk && isFuelInputOk) {
                testInputTemp() //temp - remove
                //todo: save data to db via view model
            }
            //todo: return to MainActivity
        }

        binding.btnCancel.setOnClickListener {
            //todo: return to MainActivity
        }

        binding.rgDate.setOnCheckedChangeListener { _, _ ->
            if (binding.rbToday.isChecked) {
                viewModel.setDayPickToday()
            } else {
                viewModel.setDayPickAnotherDay()
            }
        }

        binding.tilDate.setEndIconOnClickListener {
            datePicker.show(supportFragmentManager, TAG)
        }

        datePicker.addOnPositiveButtonClickListener {
            binding.tilDate.editText?.setText(it.toDateString())
            viewModel.setDayPickAnotherDay(it)
        }
    }

    private fun isInputPositiveNumber(til: TextInputLayout): Boolean {
        val input = til.editText?.text.toString()
        val isNotBlank = input.isNotBlank()
        if (isNotBlank.not()) {
            til.error = "Required"
            return false
        } else {
            til.error = null
        }
        val number = input.toIntOrNull()
        val isPositive = number?.let { it > 0 } ?: false
        if (isPositive.not()) {
            til.error = "Required positive number"
            return false
        } else {
            til.error = null
        }
        return true
    }

    private fun testInputTemp() {
        val bs = StringBuilder()
        bs.appendLine(binding.tilOdometerStatus.editText?.text.toString())
        bs.appendLine(binding.tilLiters.editText?.text.toString())
        bs.appendLine(viewModel.payPick.value?.second?.toDateString())
        bs.appendLine(binding.tilComment.editText?.text.toString())
        flashMessage(this, bs.toString())
    }

}