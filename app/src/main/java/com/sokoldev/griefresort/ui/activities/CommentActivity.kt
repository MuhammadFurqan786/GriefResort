package com.sokoldev.griefresort.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.sokoldev.griefresort.R
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

    override fun onMenuClick(position: Int, menu: AppCompatImageView, comment: Comment) {
        val popup = PopupMenu(menu.context, menu, Gravity.END)
        popup.inflate(R.menu.options_menu_comment)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete -> {
                    viewModel.removeComment(comment.id.toString(), comment.commentId.toString())
                    adapter.removeComment(position)
                    adapter.notifyItemRemoved(position)
                    true
                }
                R.id.edit -> {
                    showEditCommentDialog(menu.context, comment)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showEditCommentDialog(context: Context, comment: Comment) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Edit Comment")

        // Create EditText
        val input = EditText(context).apply {
            setText(comment.comment) // Pre-fill existing comment
            setSelection(text.length) // Move cursor to end
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        builder.setView(input)

        // Set buttons
        builder.setPositiveButton("Save") { dialog, _ ->
            val updatedText = input.text.toString().trim()
            if (updatedText.isNotEmpty()) {
                comment.comment = updatedText
                viewModel.updateComment(comment.id.toString(), comment.commentId.toString(), updatedText)
                adapter.notifyDataSetChanged()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }


}