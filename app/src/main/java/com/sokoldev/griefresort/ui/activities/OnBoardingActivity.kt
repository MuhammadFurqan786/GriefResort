package com.sokoldev.griefresort.ui.activities

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.databinding.ActivityMainBinding

class OnBoardingActivity : AppCompatActivity() {
            private lateinit var appBarConfiguration: AppBarConfiguration
            private lateinit var navController: NavController
            private lateinit var binding: ActivityMainBinding

            override fun onCreate(savedInstanceState: Bundle?) {
                WindowCompat.setDecorFitsSystemWindows(window, false)
                super.onCreate(savedInstanceState)

                binding = ActivityMainBinding.inflate(layoutInflater)
                setContentView(binding.root)

                val navHostFragment = supportFragmentManager.findFragmentById(binding.navHostFragmentContainer.id) as NavHostFragment? ?: return
                navController = navHostFragment.navController


            }



}