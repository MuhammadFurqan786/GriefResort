package com.sokoldev.griefresort.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.get
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.databinding.ActivityHomeBinding
import com.sokoldev.griefresort.utils.Constants

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        navView.setupWithNavController(navController)




        binding.back.setOnClickListener {

            with(findNavController(R.id.nav_host_fragment_activity_home)) {
                if (currentDestination == graph[R.id.settingFragment]) {
                    findNavController(R.id.nav_host_fragment_activity_home).navigateUp()
                } else if (currentDestination == graph[R.id.groupHug]) {
                    findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.action_groupHug_to_settingFragment)
                } else if (currentDestination == graph[R.id.memoryBox]) {
                    findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.action_memoryBox_to_settingFragment)
                } else if (currentDestination == graph[R.id.library]) {
                    findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.action_library_to_settingFragment)
                } else if (currentDestination == graph[R.id.remindMe]) {
                    findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.action_remindMe_to_settingFragment)
                } else if (currentDestination == graph[R.id.myDiary]) {
                    findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.action_myDiary_to_settingFragment)
                }

            }
        }

        val intent = intent
        if (intent.getStringExtra(Constants.TYPE).equals("RENEW")) {
            findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.action_groupHug_to_settingFragment)
        }
    }

}