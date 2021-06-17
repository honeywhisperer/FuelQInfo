package hr.trailovic.fuelqinfo.view.add

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import hr.trailovic.fuelqinfo.databinding.ActivityAddBinding
import hr.trailovic.fuelqinfo.model.DateOption
import hr.trailovic.fuelqinfo.model.FuelRecord
import hr.trailovic.fuelqinfo.toLongDateString
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
        bind()
    }

    private fun bind() {
        viewModel.dayPick.observe(this) { dateOptionAndDate ->
            when (dateOptionAndDate.first) {
                DateOption.TODAY -> {
                    binding.layoutAddInputFields.tilDate.visibility = View.GONE
                    binding.layoutAddInputFields.rbToday.isChecked = true
                }
                DateOption.ANOTHER_DAY -> {
                    binding.layoutAddInputFields.tilDate.visibility = View.VISIBLE
                    binding.layoutAddInputFields.tilDate.editText?.setText(dateOptionAndDate.second.toLongDateString())
                    binding.layoutAddInputFields.rbAnotherDay.isChecked = true
                }
            }
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

        toBeEdited?.let {
            viewModel.setDateWhenEditing(it.date)
            binding.layoutAddInputFields.tilOdometerStatus.editText?.setText(it.odometer.toString())
            binding.layoutAddInputFields.tilLiters.editText?.setText(it.liters.toString())
            binding.layoutAddInputFields.tilComment.editText?.setText(it.comment)
            binding.layoutAddInputFields.btnDelete.visibility = View.VISIBLE
        } ?: run {
            binding.layoutAddInputFields.btnDelete.visibility = View.GONE
        }
    }

    private fun setListeners() {

        binding.layoutAddInputFields.btnSave.setOnClickListener {
            val isOdometerInputOk =
                isInputPositiveInt(binding.layoutAddInputFields.tilOdometerStatus)
            val isFuelInputOk = isInputPositiveDouble(binding.layoutAddInputFields.tilLiters)
            if (isOdometerInputOk && isFuelInputOk) {
                with(binding.layoutAddInputFields) {
                    val odometer = tilOdometerStatus.editText?.text.toString().toInt()
                    val liters = tilLiters.editText?.text.toString().toDouble()
                    val comment = tilComment.editText?.text.toString()

                    toBeEdited?.let {
                        viewModel.updateFuelRecordData(it, odometer, liters, comment)
                    } ?: viewModel.saveFuelRecordData(odometer, liters, comment)
                }
                setResult(Activity.RESULT_OK)
                finish()
            }
        }

        binding.layoutAddInputFields.btnCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        binding.layoutAddInputFields.btnDelete.setOnClickListener {
            //todo: add dialog + action to delete single record
            AlertDialog.Builder(this)
                .setTitle("Delete this record from the database?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Delete") { _, _ ->
                    toBeEdited?.let {
                        viewModel.removeFuelRecord(it)
                        Log.d(TAG, "delete: ${it.odometer} : ${it.liters}")
                        finish()
                    } ?: run {
                        Log.e(TAG, "delete: error")
                    }
                }
                .show()
        }

        binding.layoutAddInputFields.rgDate.setOnCheckedChangeListener { _, _ ->
            if (binding.layoutAddInputFields.rbToday.isChecked) {
                viewModel.setDayPickToday()
            } else {
                toBeEdited?.let { viewModel.setDayPickAnotherDay(it.date) }
                    ?: viewModel.setDayPickAnotherDay()
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

    private fun isInputPositiveInt(til: TextInputLayout): Boolean {
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

    private fun isInputPositiveDouble(til: TextInputLayout): Boolean {
        val input = til.editText?.text.toString()
        val isNotBlank = input.isNotBlank()
        if (isNotBlank.not()) {
            til.error = "Required"
            return false
        } else {
            til.error = null
        }
        val number = input.toDoubleOrNull()
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