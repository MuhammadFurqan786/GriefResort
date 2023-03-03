package com.sokoldev.griefresort.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sokoldev.griefresort.databinding.ActivityAddReminderBinding
import com.sokoldev.griefresort.utils.Constants

class AddReminderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddReminderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.getStringExtra(Constants.TITLE) != null){
            binding.addDate.setText(intent.getStringExtra(Constants.TITLE))
        }

        binding.back.setOnClickListener {
            finish()
        }
        binding.btnSave.setOnClickListener { finish() }
    }
}