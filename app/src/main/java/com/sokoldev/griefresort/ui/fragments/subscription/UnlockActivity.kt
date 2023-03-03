package com.sokoldev.griefresort.ui.fragments.subscription

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sokoldev.griefresort.databinding.ActivityUnlockBinding
import com.sokoldev.griefresort.ui.activities.AuthActivity
import com.sokoldev.griefresort.utils.Constants

class UnlockActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUnlockBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUnlockBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.close.setOnClickListener {
            var intent = Intent(this, AuthActivity::class.java)
            intent.putExtra(Constants.TYPE,Constants.AGAIN)
            startActivity(intent)
        }
        binding.btnSubscribe.setOnClickListener {
            startActivity(Intent(this, AuthActivity::class.java))
        }
    }
}