package com.sokoldev.griefresort.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sokoldev.griefresort.databinding.ActivityNewWelcomeBinding

class NewWelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener {
            startActivity(Intent(this, GetStartedActivity::class.java))
        }

    }
}