package com.sokoldev.griefresort.data.adapters


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.models.Diary
import com.sokoldev.griefresort.ui.activities.CommentActivity
import com.sokoldev.griefresort.utils.Global
import io.github.kozemirov.readmoretextview.ReadMoreTextView


class DiaryAdapter(var clickListener: OnDiaryItemClickListener) :
    RecyclerView.Adapter<DiaryAdapter.DataObjectHolder>() {

    lateinit var context: Context

    var arraylist = mutableListOf<Diary>()
    fun setList(list: List<Diary>) {
        this.arraylist = list.toMutableList()
        notifyDataSetChanged()
    }

    interface OnDiaryItemClickListener {
        fun onMenuImageClick(
            position: Int,
            menuImage: AppCompatImageView,
        )

    }



    class DataObjectHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var support: LinearLayout? = itemView?.findViewById(R.id.support)
        var desc: ReadMoreTextView? = itemView?.findViewById(R.id.desc)
        var userName: AppCompatTextView? = itemView?.findViewById(R.id.userName)
        var date: AppCompatTextView? = itemView?.findViewById(R.id.date)
        var like: AppCompatTextView? = itemView?.findViewById(R.id.like)
        var comment: AppCompatTextView? = itemView?.findViewById(R.id.comment)
        var recyclerview: RecyclerView? = itemView?.findViewById(R.id.recyclerviewComment)
        var menuImage: AppCompatImageView? = itemView?.findViewById(R.id.menu)


    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataObjectHolder {
        context = parent.context
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_my_diary, parent, false)
        return DataObjectHolder(view)
    }

    override fun onBindViewHolder(holder: DataObjectHolder, position: Int) {

        val diary = arraylist[position]

        holder.userName?.text = diary.userName
        holder.date?.text = diary.date
        holder.comment?.text = diary.totalComments
        holder.like?.text = diary.totalHugs
        holder.desc?.text = diary.description



        val adapter = DiaryCommentAdapter(diary.listComments as ArrayList<Diary.Comments>)
        holder.recyclerview?.layoutManager = LinearLayoutManager(context)
        holder.recyclerview?.adapter = adapter


        holder.menuImage!!.setOnClickListener {
            clickListener.onMenuImageClick(position, holder.menuImage!!)
        }

        holder.support!!.setOnClickListener {
            context.startActivity(Intent(context, CommentActivity::class.java))
        }

    }

    override fun getItemCount(): Int {
        return arraylist.size
    }


}