package com.sokoldev.griefresort.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sokoldev.griefresort.databinding.ActivityShareDiaryBinding
import com.sokoldev.griefresort.utils.Constants

class ShareDiaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShareDiaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShareDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val desc = intent.getStringExtra(Constants.TYPE)
        binding.addStory.setText(desc)

        binding.back.setOnClickListener {
            finish()
        }
        binding.btnSave.setOnClickListener { finish() }
    }
}