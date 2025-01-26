package com.sokoldev.griefresort.data.adapters


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.models.Quote


class QuotesAdapter(
    val arrayList: List<Quote>
) : RecyclerView.Adapter<QuotesAdapter.DataObjectHolder>() {


    class DataObjectHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var quote: AppCompatTextView? = itemView?.findViewById(R.id.quote)
        var author: AppCompatTextView? = itemView?.findViewById(R.id.author)


    }


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): DataObjectHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_quotes, parent, false)
        return DataObjectHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DataObjectHolder, position: Int) {

        val quote = arrayList[position]

        holder.quote?.text = quote.quote
        holder.author?.text = "~ "+quote.author

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


}