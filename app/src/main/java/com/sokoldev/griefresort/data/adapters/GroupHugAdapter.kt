package com.sokoldev.griefresort.data.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.models.GroupHug
import io.github.kozemirov.readmoretextview.ReadMoreTextView


class GroupHugAdapter(
    clickListener: OnGroupHugItemsClickListener
) :
    RecyclerView.Adapter<GroupHugAdapter.DataObjectHolder>() {


    var arraylist = mutableListOf<GroupHug>()
    fun setList(list: List<GroupHug>) {
        this.arraylist = list.toMutableList()
        notifyDataSetChanged()
    }

    var clickListener: OnGroupHugItemsClickListener = clickListener

    interface OnGroupHugItemsClickListener {
        fun onGroupHugClick(
            position: Int,
            like: AppCompatTextView?
        )

        fun onSupportclick(
            position: Int,
            comment: AppCompatTextView?,
            ed_support: AppCompatEditText?
        )

    }

    class DataObjectHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var groupHug: LinearLayout? = itemView?.findViewById(R.id.sendHug)
        var support: LinearLayout? = itemView?.findViewById(R.id.support)
        var desc: ReadMoreTextView? = itemView?.findViewById(R.id.desc)
        var userName: AppCompatTextView? = itemView?.findViewById(R.id.userName)
        var date: AppCompatTextView? = itemView?.findViewById(R.id.date)
        var ed_support: AppCompatEditText? = itemView?.findViewById(R.id.ed_support)
        var like: AppCompatTextView? = itemView?.findViewById(R.id.like)
        var comment: AppCompatTextView? = itemView?.findViewById(R.id.comment)


    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataObjectHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_group_hug, parent, false)
        return DataObjectHolder(view)
    }

    override fun onBindViewHolder(holder: DataObjectHolder, position: Int) {

        val groupHug = arraylist[position]

        holder.userName?.text = groupHug.userName
        holder.date?.text = groupHug.date
        holder.comment?.text = groupHug.totalComments
        holder.like?.text = groupHug.totalHugs
        holder.desc?.text = groupHug.description

        holder.groupHug!!.setOnClickListener { v: View? ->
            clickListener.onGroupHugClick(
                position,
                holder.like
            )
        }
        holder.support!!.setOnClickListener { v: View? ->
            clickListener.onSupportclick(
                position,
                holder.comment, holder.ed_support
            )
        }
    }

    override fun getItemCount(): Int {
        return arraylist.size
    }


}