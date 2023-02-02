package com.sokoldev.griefresort.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sokoldev.griefresort.data.adapters.QuotesAdapter
import com.sokoldev.griefresort.data.viewmodel.QuotesViewModel
import com.sokoldev.griefresort.databinding.ActivityQuotesBinding

class QuotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuotesBinding
    private lateinit var viewModel: QuotesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvQuotes.layoutManager = LinearLayoutManager(this)
        binding.rvQuotes.setHasFixedSize(true)

        viewModel = ViewModelProvider(this)[QuotesViewModel::class.java]
        setUpObserver()

        binding.back.setOnClickListener {
            finish()
        }
    }

    private fun setUpObserver() {
        viewModel.getList().observe(this) {
            it.let {
                val adapter = QuotesAdapter(it)
                binding.rvQuotes.adapter = adapter
            }
        }
    }
}