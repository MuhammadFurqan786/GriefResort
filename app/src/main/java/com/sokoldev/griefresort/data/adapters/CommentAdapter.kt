package com.sokoldev.griefresort.data.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.models.Comment


class CommentAdapter(private var clickListener: OnCommentAdapterClickListener) :
    RecyclerView.Adapter<CommentAdapter.DataObjectHolder>() {

    lateinit var context: Context

    var arraylist = mutableListOf<Comment>()
    fun setList(list: List<Comment>) {
        this.arraylist = list.toMutableList()
        notifyDataSetChanged()
    }

    interface OnCommentAdapterClickListener {
        fun onReplyClick(
            position: Int
        )

    }


    class DataObjectHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var name: AppCompatTextView? = itemView?.findViewById(R.id.name)
        var comment: AppCompatTextView? = itemView?.findViewById(R.id.comment)
        var reply: AppCompatTextView? = itemView?.findViewById(R.id.reply)


    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataObjectHolder {
        context = parent.context
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comment, parent, false)
        return DataObjectHolder(view)
    }

    override fun onBindViewHolder(holder: DataObjectHolder, position: Int) {

        val comment = arraylist[position]

        holder.name?.text = comment.userName
        holder.comment?.text = comment.comment





        holder.reply!!.setOnClickListener {
            clickListener.onReplyClick(position)
        }


    }

    override fun getItemCount(): Int {
        return arraylist.size
    }


}