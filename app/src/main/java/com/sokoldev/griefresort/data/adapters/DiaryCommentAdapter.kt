package com.sokoldev.griefresort.data.adapters


import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.models.Diary
import com.sokoldev.griefresort.ui.fragments.mydiary.MyDiaryFragment
import com.sokoldev.griefresort.utils.Constants


class DiaryCommentAdapter(
    val arrayList: ArrayList<Diary.Comments>
) : RecyclerView.Adapter<DiaryCommentAdapter.DataObjectHolder>() {

    private lateinit var context: Context

    class DataObjectHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var name: AppCompatTextView? = itemView?.findViewById(R.id.name)
        var comment: AppCompatTextView? = itemView?.findViewById(R.id.comment)
        var user_comment: AppCompatTextView? = itemView?.findViewById(R.id.reply)
        var time: AppCompatTextView? = itemView?.findViewById(R.id.time)
        var user_time: AppCompatTextView? = itemView?.findViewById(R.id.time_reply)
        var other_user_card: RelativeLayout? =
            itemView?.findViewById(R.id.other_user_comment_layout)
        var user_card: CardView? = itemView?.findViewById(R.id.user_comment)
        var replyButton: AppCompatTextView? = itemView?.findViewById(R.id.replyButton)
        var menuUser: AppCompatImageView? = itemView?.findViewById(R.id.menuUser)
        var menuOtherUser: AppCompatImageView? = itemView?.findViewById(R.id.menu_other_user)


    }


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): DataObjectHolder {
        context = parent.context
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_child_my_diary, parent, false)
        return DataObjectHolder(view)
    }

    override fun onBindViewHolder(holder: DataObjectHolder, position: Int) {

        val comments = arrayList[position]

        if (arrayList[position].type.equals(Constants.OTHERUSERCARD)) {
            holder.user_card?.visibility = View.GONE
            holder.other_user_card?.visibility = View.VISIBLE
            holder.name?.text = comments.name
            holder.comment?.text = comments.comment
            holder.time?.text = comments.time
        } else {
            holder.other_user_card?.visibility = View.GONE
            holder.user_card?.visibility = View.VISIBLE
            holder.user_comment?.text = comments.comment
            holder.time?.text = comments.time
        }

        holder.replyButton!!.setOnClickListener {
            MyDiaryFragment.binding.linearLayout.visibility = View.VISIBLE
        }

        holder.menuUser?.setOnClickListener {
            showDialog(position, context, holder.menuUser!!)
        }

        holder.menuOtherUser?.setOnClickListener {
            showDialog(position, context, holder.menuOtherUser!!)
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    fun showDialog(position: Int, context: Context, menuUser: AppCompatImageView) {
        val popup = this.context?.let { PopupMenu(it, menuUser, Gravity.END) }
        popup?.inflate(R.menu.options_menu_comment)
        popup?.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.report) {
                showReportDialog()
                return@setOnMenuItemClickListener true
            } else if (item.itemId == R.id.delete) {
                arrayList.removeAt(position)
                notifyItemRemoved(position)
                return@setOnMenuItemClickListener true
            } else return@setOnMenuItemClickListener item.itemId == R.id.delete
        }
        popup?.show()
    }

    private fun showReportDialog() {
        val alertDialog: android.app.AlertDialog? =
            android.app.AlertDialog.Builder(context)
                .setMessage("Thank You for Reporting, we are looking into this.") //set positive button
                .setPositiveButton(
                    "ok",
                    DialogInterface.OnClickListener { dialogInterface, i -> //set what would happen when positive button is clicked
                        dialogInterface.dismiss()
                    })
                .show()
    }

}