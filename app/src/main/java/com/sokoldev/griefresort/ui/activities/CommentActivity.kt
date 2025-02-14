package com.sokoldev.griefresort.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sokoldev.griefresort.data.adapters.CommentAdapter
import com.sokoldev.griefresort.data.models.Comment
import com.sokoldev.griefresort.data.viewmodel.GroupHugViewModel
import com.sokoldev.griefresort.databinding.ActivityCommentBinding
import com.sokoldev.griefresort.utils.Constants

class CommentActivity : AppCompatActivity(), CommentAdapter.OnCommentAdapterClickListener {
    private lateinit var binding: ActivityCommentBinding
    private lateinit var adapter: CommentAdapter
    private val viewModel: GroupHugViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the list of Comment objects
        val comments: ArrayList<Comment>? =
            intent.getParcelableArrayListExtra(Constants.COMMENT_LIST)

        if (comments != null) {
            adapter = CommentAdapter(this)
            adapter.setList(comments)
            binding.rvSupport.adapter = adapter
        }

        binding.rvSupport.layoutManager = LinearLayoutManager(this)
        binding.rvSupport.setHasFixedSize(true)

        binding.back.setOnClickListener {
            startActivity(Intent(this@CommentActivity, HomeActivity::class.java))
            finish()
        }
        binding.sendSupport.setOnClickListener {
            binding.linearLayout.visibility = View.GONE
        }

        initObserver()


    }

    private fun initObserver() {
        viewModel.success.observe(this) {
            Toast.makeText(this@CommentActivity, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.error.observe(this) {
            Toast.makeText(this@CommentActivity, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.comments.observe(this) {
            if (it != null) {
                adapter = CommentAdapter(this)
                adapter.setList(it)
                binding.rvSupport.adapter = adapter
            }
        }
    }

    override fun onDeleteClick(comment: Comment, position: Int) {
        adapter.removeComment(position)
        viewModel.removeComment(comment.id.toString(), comment.commentId.toString())
    }
}