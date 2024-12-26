package com.sokoldev.griefresort.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.sokoldev.griefresort.data.adapters.QuotesAdapter
import com.sokoldev.griefresort.data.models.Item
import com.sokoldev.griefresort.data.viewmodel.ItemViewModel
import com.sokoldev.griefresort.databinding.ActivityQuotesBinding

class QuotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuotesBinding
    private lateinit var viewModel: ItemViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvQuotes.layoutManager = layoutManager
        binding.rvQuotes.setHasFixedSize(true)

        viewModel = ViewModelProvider(this)[ItemViewModel::class.java]
        viewModel.loadQuotes()
        setUpObserver()

        // SnapHelper to ensure only one item is visible at a time
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvQuotes)

// Ensure that the current item fills the screen by setting its height to match_parent
        binding.rvQuotes.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                // Adjust the height of the current item to fill the screen and hide others
                if (firstVisibleItemPosition == lastVisibleItemPosition) {
                    val firstVisibleChild = recyclerView.getChildAt(0)
                    firstVisibleChild.layoutParams.height = recyclerView.height
                }
            }
        })


        binding.back.setOnClickListener {
            finish()
        }
    }

    private fun setUpObserver() {
        viewModel.quotes.observe(this) {
            it.let {
                val adapter = QuotesAdapter(it)
                binding.rvQuotes.adapter = adapter
            }
        }
    }
}