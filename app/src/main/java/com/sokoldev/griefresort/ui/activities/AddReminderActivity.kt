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
    private var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.getStringExtra(Constants.TITLE) != null) {
            binding.addDate.setText(intent.getStringExtra(Constants.TITLE))
            reminderId = intent.getStringExtra(Constants.ID)
            isUpdate = intent.getBooleanExtra(Constants.EDIT, false)
            binding.btnSave.text = "Update"
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
            val selectedDate = "$year-${month + 1}-$day 00:00" // Assuming time is 00:00

            val title = binding.addDate.text.toString()

            if (title.isEmpty()) {
                binding.addDate.error = "Please enter title"
            } else if (selectedDate.isEmpty()) {
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