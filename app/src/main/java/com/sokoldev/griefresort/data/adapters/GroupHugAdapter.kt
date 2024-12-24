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
import androidx.recyclerview.widget.RecyclerView
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.models.Comment
import com.sokoldev.griefresort.data.models.GroupHug
import com.sokoldev.griefresort.ui.activities.CommentActivity
import com.sokoldev.griefresort.utils.Constants
import io.github.kozemirov.readmoretextview.ReadMoreTextView


class GroupHugAdapter(
    clickListener: OnGroupHugItemsClickListener
) :
    RecyclerView.Adapter<GroupHugAdapter.DataObjectHolder>() {


    var arraylist = mutableListOf<GroupHug>()
    private lateinit var context: Context
    fun setList(list: List<GroupHug>) {
        this.arraylist = list.toMutableList()
        notifyDataSetChanged()
    }

    var clickListener: OnGroupHugItemsClickListener = clickListener

    interface OnGroupHugItemsClickListener {
        fun onGroupHugClick(
            id: String,
            totalHugs: Int?,
            like: AppCompatTextView?
        )

        fun onSupportClick(
           id:String,
           commentText: AppCompatTextView?,
            ed_support: AppCompatEditText?,
            totalComments: String
        )

        fun onMenuImageClick(
            position: Int,
            menuImage: AppCompatImageView,
        )

    }

    class DataObjectHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var groupHug: LinearLayout? = itemView?.findViewById(R.id.sendHug)
        var support: LinearLayout? = itemView?.findViewById(R.id.support)
        var desc: ReadMoreTextView? = itemView?.findViewById(R.id.desc)
        var userName: AppCompatTextView? = itemView?.findViewById(R.id.userName)
        var ed_support: AppCompatEditText? = itemView?.findViewById(R.id.ed_support)
        var like: AppCompatTextView? = itemView?.findViewById(R.id.like)
        var comment: AppCompatTextView? = itemView?.findViewById(R.id.comment)
        var menuImage: AppCompatImageView? = itemView?.findViewById(R.id.menu)
        var sendSupport: AppCompatImageView? = itemView?.findViewById(R.id.sendSupport)


    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataObjectHolder {
        context = parent.context
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_group_hug, parent, false)
        return DataObjectHolder(view)
    }

    override fun onBindViewHolder(holder: DataObjectHolder, position: Int) {

        val groupHug = arraylist[position]

        holder.userName?.text = groupHug.userName
        holder.comment?.text = groupHug.totalComments.toString()
        holder.like?.text = groupHug.totalHugs.toString()
        holder.desc?.text = groupHug.description

        holder.support!!.setOnClickListener {
            val intent = Intent(context, CommentActivity::class.java)
            intent.putParcelableArrayListExtra(
                Constants.COMMENT_LIST,
                arraylist[position].comments as ArrayList<Comment>
            )
            context.startActivity(intent)
        }

        holder.groupHug!!.setOnClickListener { v: View? ->
            groupHug.id?.let {
                clickListener.onGroupHugClick(
                    it,
                    groupHug.totalHugs,
                    holder.like
                )
            }
        }
        holder.sendSupport!!.setOnClickListener { v: View? ->
            groupHug.id?.let {
                clickListener.onSupportClick(
                    it,
                    holder.comment,
                    holder.ed_support,
                    arraylist[position].totalComments.toString()
                )
            }
        }

        holder.menuImage!!.setOnClickListener {
            clickListener.onMenuImageClick(position, holder.menuImage!!)
        }
    }

    override fun getItemCount(): Int {
        return arraylist.size
    }


}