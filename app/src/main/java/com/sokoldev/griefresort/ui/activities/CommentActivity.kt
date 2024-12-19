package com.sokoldev.griefresort.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sokoldev.griefresort.data.adapters.CommentAdapter
import com.sokoldev.griefresort.data.models.Comment
import com.sokoldev.griefresort.databinding.ActivityCommentBinding
import com.sokoldev.griefresort.utils.Constants

class CommentActivity : AppCompatActivity(), CommentAdapter.OnCommentAdapterClickListener {
    private lateinit var binding: ActivityCommentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the list of Comment objects
        val comments: ArrayList<Comment>? =
            intent.getParcelableArrayListExtra(Constants.COMMENT_LIST)

        if (comments != null) {
            val adapter = CommentAdapter(this)
            adapter.setList(comments)
            binding.rvSupport.adapter = adapter
        }

        binding.rvSupport.layoutManager = LinearLayoutManager(this)
        binding.rvSupport.setHasFixedSize(true)

        binding.back.setOnClickListener {
            finish()
        }
        binding.sendSupport.setOnClickListener {
            binding.linearLayout.visibility = View.GONE
        }
    }

    override fun onReplyClick(position: Int) {
        binding.linearLayout.visibility = View.VISIBLE
    }
}