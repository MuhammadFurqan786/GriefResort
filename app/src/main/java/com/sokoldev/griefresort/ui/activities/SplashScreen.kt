package com.sokoldev.griefresort.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.sokoldev.griefresort.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

       val handler = Handler()
        handler.postDelayed(Runnable {
            val intent = Intent(this@SplashScreen, OnBoardingActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}