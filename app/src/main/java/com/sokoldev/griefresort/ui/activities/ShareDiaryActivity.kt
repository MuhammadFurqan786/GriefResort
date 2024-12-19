package com.sokoldev.griefresort.ui.activities

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.sokoldev.griefresort.data.models.GroupHug
import com.sokoldev.griefresort.data.viewmodel.GroupHugViewModel
import com.sokoldev.griefresort.databinding.ActivityShareDiaryBinding
import com.sokoldev.griefresort.preference.PreferenceHelper
import com.sokoldev.griefresort.utils.Constants
import com.sokoldev.griefresort.utils.Global

class ShareDiaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShareDiaryBinding
    private val viewModel: GroupHugViewModel by viewModels()
    private lateinit var helper: PreferenceHelper

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShareDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        helper = PreferenceHelper.getPref(this)

        val intent = intent
        val desc = intent.getStringExtra(Constants.TYPE)
        binding.addStory.setText(desc)

        binding.back.setOnClickListener {
            finish()
        }
        binding.buttonshare.setOnClickListener {
            addGroupHug()
        }

        initObserver()
    }

    private fun initObserver() {
        viewModel.error.observe(this, Observer {
            Global.showErrorMessage(binding.root, it)
        })
        viewModel.success.observe(this, Observer {
            Global.showMessage(binding.root, it)
            finish()
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addGroupHug() {
        val desc = binding.addStory.text.toString()
        val userId = helper.getCurrentUser()?.userId
        val userName = helper.getCurrentUser()?.userName
        val date = Global.getCurrentFormattedDateTime()
        if (desc.isNotEmpty()) {
            val groupHug =
                GroupHug(
                    userId = userId,
                    description = desc,
                    totalHugs = 0,
                    totalComments = 0,
                    comments = emptyList(),
                    userName = userName,
                    date = date
                )
            viewModel.addGroupHug(groupHug)
        }
    }

}