package com.sokoldev.griefresort.data.adapters


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.models.Book


class BooksAdapter(
    val context: Context,
    val arrayList: List<Book>
) : RecyclerView.Adapter<BooksAdapter.DataObjectHolder>() {


    class DataObjectHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var image: AppCompatImageView? = itemView?.findViewById(R.id.image)
        var name: AppCompatTextView? = itemView?.findViewById(R.id.name)
        var author: AppCompatTextView? = itemView?.findViewById(R.id.author)
        var summary: AppCompatTextView? = itemView?.findViewById(R.id.summary)


    }


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): DataObjectHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_books, parent, false)
        return DataObjectHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DataObjectHolder, position: Int) {

        val item = arrayList[position]

        holder.name?.text = item.title
        holder.author?.text = context.getString(R.string.author, item.author);
        holder.summary?.text = item.summary
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


}