package hr.trailovic.fuelqinfo.view.add

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import hr.trailovic.fuelqinfo.databinding.ActivityAddBinding
import hr.trailovic.fuelqinfo.flashMessage
import hr.trailovic.fuelqinfo.model.DateOption
import hr.trailovic.fuelqinfo.model.FuelRecord
import hr.trailovic.fuelqinfo.toDateString
import hr.trailovic.fuelqinfo.viewmodel.AddViewModel
import hr.trailovic.weatherqinfo.base.BaseActivity

@AndroidEntryPoint
class AddActivity : BaseActivity<ActivityAddBinding>() {

    private val viewModel: AddViewModel by viewModels()
    private lateinit var datePicker: MaterialDatePicker<Long>
    private var toBeEdited: FuelRecord? = null


    override fun getBinding(): ActivityAddBinding {
        return ActivityAddBinding.inflate(layoutInflater)
    }

    override fun onStart() {
        super.onStart()
        intent?.let {
            toBeEdited = it.getParcelableExtra(ARG_EDIT_FUEL_RECORD)
        }
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
            .setSelection(viewModel.dayPick.value?.second)
            .build()
    }

    private fun setView() {
        binding.topAppBar.title = if (toBeEdited != null)
            "Edit Fuel Record"
        else
            "Add New Fuel Record"

        viewModel.dayPick.observe(this) { dateOptionAndDate ->
            when (dateOptionAndDate.first) {
                DateOption.TODAY -> {
                    binding.layoutAddInputFields.tilDate.visibility = View.GONE
                }
                DateOption.ANOTHER_DAY -> {
                    binding.layoutAddInputFields.tilDate.visibility = View.VISIBLE
                    binding.layoutAddInputFields.tilDate.editText?.setText(dateOptionAndDate.second.toDateString())
                }
            }
        }
    }

    private fun setListeners() {
        binding.layoutAddInputFields.btnSave.setOnClickListener {
            val isOdometerInputOk =
                isInputPositiveNumber(binding.layoutAddInputFields.tilOdometerStatus)
            val isFuelInputOk = isInputPositiveNumber(binding.layoutAddInputFields.tilLiters)
            if (isOdometerInputOk && isFuelInputOk) {
//                testInputTemp() //temp - remove

                with(binding.layoutAddInputFields) {
                    val odometer = tilOdometerStatus.editText?.text.toString().toInt()
                    val liters = tilLiters.editText?.text.toString().toDouble()
//                    val date = viewModel.payPick.value?.second ?: 0
                    val comment = tilComment.editText?.text.toString()

                    toBeEdited?.let {
                        viewModel.updateFuelRecordData(it, odometer, liters, comment)
                    } ?: viewModel.saveFuelRecordData(odometer, liters, comment)
                }
            }
            //todo: return to MainActivity
        }

        binding.layoutAddInputFields.btnCancel.setOnClickListener {
            //todo: return to MainActivity
        }

        binding.layoutAddInputFields.btnCancel.setOnLongClickListener {
            viewModel.removeAllFuelRecords()
            true
        }

        binding.layoutAddInputFields.rgDate.setOnCheckedChangeListener { _, _ ->
            if (binding.layoutAddInputFields.rbToday.isChecked) {
                viewModel.setDayPickToday()
            } else {
                viewModel.setDayPickAnotherDay()
            }
        }

        binding.layoutAddInputFields.tilDate.setEndIconOnClickListener {
            datePicker.show(supportFragmentManager, TAG)
        }

        datePicker.addOnPositiveButtonClickListener {
            binding.layoutAddInputFields.tilDate.editText?.setText(it.toDateString())
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
        bs.appendLine(binding.layoutAddInputFields.tilOdometerStatus.editText?.text.toString())
        bs.appendLine(binding.layoutAddInputFields.tilLiters.editText?.text.toString())
        bs.appendLine(viewModel.dayPick.value?.second?.toDateString())
        bs.appendLine(binding.layoutAddInputFields.tilComment.editText?.text.toString())
        flashMessage(this, bs.toString())
    }

    companion object {
        private const val TAG = "AddActivity:::"
        private const val ARG_EDIT_FUEL_RECORD = "22121"
        fun createIntent(context: Context, fuelRecord: FuelRecord?) =
            Intent(context, AddActivity::class.java).apply {
                fuelRecord?.let {
                    putExtra(ARG_EDIT_FUEL_RECORD, it)
                }
            }
    }

}