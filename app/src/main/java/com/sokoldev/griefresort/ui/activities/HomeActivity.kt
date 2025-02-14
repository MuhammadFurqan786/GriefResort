package com.sokoldev.griefresort.ui.activities

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.databinding.ActivityHomeBinding
import com.sokoldev.griefresort.utils.Constants

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    private var isSettingOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate binding and set the content view
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize BottomNavigationView and NavController
        val navView: BottomNavigationView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_activity_home)

        // Set up the BottomNavigationView with the NavController
        navView.setupWithNavController(navController)
        navView.itemIconTintList = null

        // Handle back button navigation
        binding.back.setOnClickListener {
            handleBackNavigation()
        }

        // Navigate to Settings if the intent is of type "RENEW"
        if (intent.getStringExtra(Constants.TYPE) == "RENEW") {
            navigateToSettings(navView)
        }
        navView.setOnItemSelectedListener { menuItem ->
            if (isSettingOpen && menuItem.itemId != R.id.settingFragment) {
                isSettingOpen = false
            }
            navController.navigate(menuItem.itemId)
            true
        }


        // Add a listener to sync the BottomNavigationView state
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.settingFragment) {
                isSettingOpen = true
                uncheckAllItems(navView.menu) // Uncheck all items for `settingFragment`
            } else {
                isSettingOpen = false
                updateBottomNavigationState(navView, destination.id)
            }
        }
    }

//    private fun handleBackNavigation() {
//        when (navController.currentDestination?.id) {
//            R.id.settingFragment -> {
//                isSettingOpen = false
//                navController.navigateUp()
//            }
//
//        }
//    }


    private fun handleBackNavigation() {
        when (navController.currentDestination?.id) {
            R.id.settingFragment -> {
                isSettingOpen = false
                navController.navigateUp()
            }
            R.id.groupHug -> {
                isSettingOpen = true
                navigateToSettings(binding.navView)

            }
            R.id.memoryBox -> {
                isSettingOpen = true
                navigateToSettings(binding.navView)
            }
            R.id.library -> {
                isSettingOpen = true
                navigateToSettings(binding.navView)

            }
            R.id.remindMe -> {
                isSettingOpen = true
                navigateToSettings(binding.navView)

            }
            R.id.myDiary -> {
                isSettingOpen = true
                navigateToSettings(binding.navView)

            }else -> {
            isSettingOpen = false
            navController.navigateUp()
        }

        }
    }

    private fun navigateToSettings(navView: BottomNavigationView) {
        uncheckAllItems(navView.menu) // Uncheck all items
        navController.navigate(R.id.settingFragment) // Navigate to Settings
        isSettingOpen = true
    }
    private fun updateBottomNavigationState(navView: BottomNavigationView, destinationId: Int) {
        // Update the selected state of the menu item corresponding to the destination
        val menuItem = navView.menu.findItem(destinationId)
        menuItem?.isChecked = true
    }


    private fun uncheckAllItems(menu: Menu) {
        menu.setGroupCheckable(0, true, false)
        for (i in 0 until menu.size()) {
            menu.getItem(i).isChecked = false
        }
        menu.setGroupCheckable(0, true, true)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}
