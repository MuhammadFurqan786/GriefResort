package com.sokoldev.griefresort.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.preference.PreferenceHelper

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    private var isUserLoggedIn = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        isUserLoggedIn = PreferenceHelper.getPref(this).isUserLogin()
        Log.d("LOGIN", isUserLoggedIn.toString())

        val handler = Handler()
        handler.postDelayed(Runnable {
            if (isUserLoggedIn) {
                val intent = Intent(this@SplashScreen, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this@SplashScreen, NewWelcomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 1500)
    }
}