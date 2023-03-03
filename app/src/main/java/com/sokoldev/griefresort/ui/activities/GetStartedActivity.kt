package com.sokoldev.griefresort.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.databinding.ActivityGetStartedBinding

class GetStartedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGetStartedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGetStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.haveAnAccount.setOnClickListener {

            binding.getMEStarted.background = resources.getDrawable(R.drawable.bg_outlined_green)
            binding.haveAnAccount.background = resources.getDrawable(R.drawable.bg_filled_green)
            binding.tvAccount.setTextColor(resources.getColor(R.color.white))
            binding.textStarted.setTextColor(resources.getColor(R.color.colorPrimary))
            startActivity(Intent(this@GetStartedActivity, AuthActivity::class.java))
        }
        binding.getMEStarted.setOnClickListener {
            binding.getMEStarted.background = resources.getDrawable(R.drawable.bg_filled_green)
            binding.haveAnAccount.background = resources.getDrawable(R.drawable.bg_outlined_green)
            binding.textStarted.setTextColor(resources.getColor(R.color.white))
            binding.tvAccount.setTextColor(resources.getColor(R.color.colorPrimary))
            startActivity(Intent(this@GetStartedActivity, OnBoardingActivity::class.java))

        }

    }
}