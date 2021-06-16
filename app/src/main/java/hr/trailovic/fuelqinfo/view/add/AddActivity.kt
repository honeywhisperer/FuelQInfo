package hr.trailovic.fuelqinfo.view.add

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import hr.trailovic.fuelqinfo.databinding.ActivityAddBinding
import hr.trailovic.fuelqinfo.displayMessage
import hr.trailovic.fuelqinfo.model.DateOption
import hr.trailovic.fuelqinfo.model.FuelRecord
import hr.trailovic.fuelqinfo.toLongDateString
import hr.trailovic.fuelqinfo.viewmodel.AddViewModel
import hr.trailovic.fuelqinfo.viewmodel.FuelViewModel
import hr.trailovic.weatherqinfo.base.BaseActivity

@AndroidEntryPoint
class AddActivity : BaseActivity<ActivityAddBinding>() {

    private val viewModel: AddViewModel by viewModels()
    private val vm: FuelViewModel by viewModels() //test
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
        bind()
    }

    private fun bind() {
        vm.fuelRecords.observe(this) {
            displayMessage(this, it)
        }
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
        binding.layoutTopAppBar.topAppBar.title = if (toBeEdited != null)
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
                    binding.layoutAddInputFields.tilDate.editText?.setText(dateOptionAndDate.second.toLongDateString())
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
                with(binding.layoutAddInputFields) {
                    val odometer = tilOdometerStatus.editText?.text.toString().toInt()
                    val liters = tilLiters.editText?.text.toString().toDouble()
                    val comment = tilComment.editText?.text.toString()

                    toBeEdited?.let {
                        viewModel.updateFuelRecordData(it, odometer, liters, comment)
                    } ?: viewModel.saveFuelRecordData(odometer, liters, comment)
                }
                //todo: return to MainActivity
                setResult(Activity.RESULT_OK)
                finish()
            }
        }

        binding.layoutAddInputFields.btnCancel.setOnClickListener {
            //todo: return to MainActivity
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        binding.layoutAddInputFields.btnCancel.setOnLongClickListener {
            viewModel.removeAllFuelRecords() // todo: remove this
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
            binding.layoutAddInputFields.tilDate.editText?.setText(it.toLongDateString())
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

    companion object {
        private const val TAG = "AddActivity:::"
        private const val ARG_EDIT_FUEL_RECORD = "22121"
        fun createIntent(context: Context, fuelRecord: FuelRecord? = null) =
            Intent(context, AddActivity::class.java).apply {
                fuelRecord?.let {
                    putExtra(ARG_EDIT_FUEL_RECORD, it)
                }
            }
    }

}