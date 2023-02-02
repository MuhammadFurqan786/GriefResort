package com.sokoldev.griefresort.data.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.models.RemindMe


class RemindMeAdapter(
    val arrayList: List<RemindMe>, clickListener: OnRemindMeItemsClickListener
) : RecyclerView.Adapter<RemindMeAdapter.DataObjectHolder>() {
    var clickListener: OnRemindMeItemsClickListener = clickListener

    interface OnRemindMeItemsClickListener {
        fun onDeleteClick(
            position: Int
        )

        fun onEditClick(
            position: Int,
        )

    }

    class DataObjectHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var edit: AppCompatImageView? = itemView?.findViewById(R.id.ic_edit)
        var delete: AppCompatImageView? = itemView?.findViewById(R.id.ic_delete)
        var title: AppCompatTextView? = itemView?.findViewById(R.id.title)
        var date: AppCompatTextView? = itemView?.findViewById(R.id.date)


    }


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): DataObjectHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_remind_me, parent, false)
        return DataObjectHolder(view)
    }

    override fun onBindViewHolder(holder: DataObjectHolder, position: Int) {

        val remindMe = arrayList[position]

        holder.title?.text = remindMe.title
        holder.date?.text = remindMe.date


        holder.edit!!.setOnClickListener { v: View? ->
            clickListener.onEditClick(
                position
            )
        }
        holder.delete!!.setOnClickListener { v: View? ->
            clickListener.onDeleteClick(
                position
            )
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


}