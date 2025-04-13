package com.sokoldev.griefresort.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sokoldev.griefresort.data.models.RemindMe
import com.sokoldev.griefresort.data.viewmodel.RemindViewModel
import com.sokoldev.griefresort.databinding.ActivityAddReminderBinding
import com.sokoldev.griefresort.preference.PreferenceHelper
import com.sokoldev.griefresort.utils.Constants
import com.sokoldev.griefresort.utils.Global

class AddReminderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddReminderBinding
    private val viewModel: RemindViewModel by viewModels()
    private var reminderId: String? = null
    private var selectedDate: String? = null
    private var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.getStringExtra(Constants.TITLE) != null) {
            binding.addDate.setText(intent.getStringExtra(Constants.TITLE))
            reminderId = intent.getStringExtra(Constants.ID)
            selectedDate = intent.getStringExtra(Constants.DATE)
            isUpdate = intent.getBooleanExtra(Constants.EDIT, false)
            binding.btnSave.text = "Update"

            // Set date picker when updating
            selectedDate?.let {
                val parts = it.split("-") // Split by "-"
                if (parts.size >= 3) {
                    val year = parts[0].toInt()
                    val month = parts[1].toInt() - 1 // Convert to 0-indexed
                    val day = parts[2].split(" ")[0].toInt() // Remove time part
                    binding.datePicker.updateDate(year, month, day) // Set to DatePicker
                }
            }
        } else {
            binding.btnSave.text = "Save"
        }



        binding.back.setOnClickListener {
            finish()
        }

        binding.btnSave.setOnClickListener {
            val day = binding.datePicker.dayOfMonth
            val month = binding.datePicker.month + 1 // Convert 0-indexed month to 1-indexed
            val year = binding.datePicker.year
            selectedDate = "$year-${month}-$day 00:00" // Assuming time is 00:00

            val title = binding.addDate.text.toString()

            if (title.isEmpty()) {
                binding.addDate.error = "Please enter title"
            } else if (selectedDate!!.isEmpty()) {
                Global.showErrorMessage(binding.root.rootView, "Please select date")
            } else {
                if (isUpdate) {
                    val reminder = RemindMe(id = reminderId, title = title, date = selectedDate)
                    PreferenceHelper.getPref(this).getCurrentUser()?.userId.let {
                        if (it != null) {
                            viewModel.updateReminder(it, reminder)
                        }
                    }
                } else {
                    val reminder = RemindMe(id = "", title = title, date = selectedDate)
                    PreferenceHelper.getPref(this).getCurrentUser()?.userId.let {
                        if (it != null) {
                            viewModel.addReminder(it, reminder)
                        }
                    }
                }
            }
        }


        initObserver()
    }

    private fun initObserver() {
        viewModel.success.observe(this) {
            Global.showMessage(binding.root.rootView, it)
            finish()
        }
        viewModel.error.observe(this) {
            Global.showErrorMessage(binding.root.rootView, it)
        }
    }
}