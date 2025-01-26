package com.sokoldev.griefresort.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.sokoldev.griefresort.data.models.Comment
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
        val groupHug = intent.getParcelableExtra<GroupHug>(Constants.GROUP_HUG)
        if (groupHug != null) {
            binding.addStory.setText(groupHug.description)
        }

        binding.back.setOnClickListener {
            finish()
        }
        binding.buttonshare.setOnClickListener {
            if (groupHug != null) {
                groupHug.description = binding.addStory.text.toString()
                groupHug.id?.let { it1 -> viewModel.editGroupHug(it1, groupHug) }
                return@setOnClickListener
            } else {
                addGroupHug()
            }
        }

        initObserver()
    }

    private fun initObserver() {
        viewModel.error.observe(this, Observer {
            Global.showErrorMessage(binding.root, it)
        })
        viewModel.success.observe(this, Observer {
            Global.showMessage(binding.root, it)
            startActivity(Intent(this, HomeActivity::class.java))
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
                    comments = ArrayList<Comment>(),
                    userName = userName,
                    date = date
                )
            viewModel.addGroupHug(groupHug)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

}