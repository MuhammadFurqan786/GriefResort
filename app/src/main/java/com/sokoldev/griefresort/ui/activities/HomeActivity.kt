package com.sokoldev.griefresort.ui.activities

import android.os.Bundle
import android.view.Menu
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

        navView.itemIconTintList = null


        binding.back.setOnClickListener {

            with(findNavController(R.id.nav_host_fragment_activity_home)) {
                if (currentDestination == graph[R.id.settingFragment]) {
                    findNavController(R.id.nav_host_fragment_activity_home).navigateUp()
                } else if (currentDestination == graph[R.id.groupHug]) {
                    uncheckAllItems(navView.menu)
                    findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.action_groupHug_to_settingFragment)
                } else if (currentDestination == graph[R.id.memoryBox]) {
                    uncheckAllItems(navView.menu)
                    findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.action_memoryBox_to_settingFragment)
                } else if (currentDestination == graph[R.id.library]) {
                    uncheckAllItems(navView.menu)
                    findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.action_library_to_settingFragment)
                } else if (currentDestination == graph[R.id.remindMe]) {
                    uncheckAllItems(navView.menu)
                    findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.action_remindMe_to_settingFragment)
                } else if (currentDestination == graph[R.id.myDiary]) {
                    uncheckAllItems(navView.menu)
                    findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.action_myDiary_to_settingFragment)
                }

            }
        }

        val intent = intent
        if (intent.getStringExtra(Constants.TYPE).equals("RENEW")) {
            findNavController(R.id.nav_host_fragment_activity_home).navigate(R.id.action_groupHug_to_settingFragment)
        }
    }

    private fun uncheckAllItems(menu: Menu) {
        menu.setGroupCheckable(0, true, false)
        for (i in 0 until menu.size()) {
            menu.getItem(i).isChecked = false
        }
        menu.setGroupCheckable(0, true, true)
    }

}