package com.sokoldev.griefresort.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sokoldev.griefresort.data.adapters.CommentAdapter
import com.sokoldev.griefresort.data.viewmodel.CommentViewModel
import com.sokoldev.griefresort.databinding.ActivityCommentBinding

class CommentActivity : AppCompatActivity(), CommentAdapter.OnCommentAdapterClickListener {
    private lateinit var binding: ActivityCommentBinding
    private lateinit var viewModel: CommentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(CommentViewModel::class.java)


        binding.rvSupport.layoutManager = LinearLayoutManager(this)
        binding.rvSupport.setHasFixedSize(true)

        initObserver()

        binding.back.setOnClickListener {
            finish()
        }
        binding.sendSupport.setOnClickListener {
            binding.linearLayout.visibility = View.GONE
        }
    }

    private fun initObserver() {
        viewModel.getList().observe(this) {
            it.let {
                val adapter = CommentAdapter(this)
                adapter.setList(it)
                binding.rvSupport.adapter = adapter
            }
        }
    }

    override fun onReplyClick(position: Int) {
        binding.linearLayout.visibility = View.VISIBLE
    }
}