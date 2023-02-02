package com.sokoldev.griefresort.data.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.models.Diary


class DiaryCommentAdapter(
    val arrayList: List<Diary.Comments>
) : RecyclerView.Adapter<DiaryCommentAdapter.DataObjectHolder>() {


    class DataObjectHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var name: AppCompatTextView? = itemView?.findViewById(R.id.name)
        var comment: AppCompatTextView? = itemView?.findViewById(R.id.comment)
        var time: AppCompatTextView? = itemView?.findViewById(R.id.time)


    }


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): DataObjectHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_child_my_diary, parent, false)
        return DataObjectHolder(view)
    }

    override fun onBindViewHolder(holder: DataObjectHolder, position: Int) {

        val comments = arrayList[position]

        holder.name?.text = comments.name
        holder.comment?.text = comments.comment
        holder.time?.text = comments.time

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


}