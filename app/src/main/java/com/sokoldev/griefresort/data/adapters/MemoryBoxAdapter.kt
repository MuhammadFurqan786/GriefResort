package com.sokoldev.griefresort.data.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.models.MemoryBox


class MemoryBoxAdapter(
    val arrayList: ArrayList<MemoryBox>,
    clickListener: OnMemoryBoxItemsClickListener
) :
    RecyclerView.Adapter<MemoryBoxAdapter.DataObjectHolder>() {
    var clickListener: OnMemoryBoxItemsClickListener = clickListener

    interface OnMemoryBoxItemsClickListener {
        fun onitemClcik(
            position: Int
        )

        fun onMenuClick(
            position: Int,
            menu: AppCompatImageView,
        )

    }

    class DataObjectHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var image: AppCompatImageView? = itemView?.findViewById(R.id.image)
        var menu: AppCompatImageView? = itemView?.findViewById(R.id.menu)


    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataObjectHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_memory_box, parent, false)
        return DataObjectHolder(view)
    }

    override fun onBindViewHolder(holder: DataObjectHolder, position: Int) {

        val memoryBox = arrayList[position]

        holder.image?.setImageResource(memoryBox.image)

        holder.menu!!.setOnClickListener { v: View? ->
            clickListener.onMenuClick(
                position, holder.menu!!
            )
        }
        holder.image!!.setOnClickListener { v: View? ->
            clickListener.onitemClcik(
                position
            )
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


    public fun removeAt(position: Int) {
        arrayList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, arrayList.size)
    }

}