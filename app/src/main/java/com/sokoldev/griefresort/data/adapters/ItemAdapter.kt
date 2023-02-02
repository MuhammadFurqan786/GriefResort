package com.sokoldev.griefresort.data.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.models.Item


class ItemAdapter(
    val arrayList: List<Item>
) : RecyclerView.Adapter<ItemAdapter.DataObjectHolder>() {


    class DataObjectHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var image: AppCompatImageView? = itemView?.findViewById(R.id.image)
        var name: AppCompatTextView? = itemView?.findViewById(R.id.name)


    }


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): DataObjectHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_books, parent, false)
        return DataObjectHolder(view)
    }

    override fun onBindViewHolder(holder: DataObjectHolder, position: Int) {

        val item = arrayList[position]

        holder.name?.text = item.name
        holder.image?.setImageResource(item.image)

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


}