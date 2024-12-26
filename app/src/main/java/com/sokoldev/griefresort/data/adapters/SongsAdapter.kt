package com.sokoldev.griefresort.data.adapters


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.models.Book
import com.sokoldev.griefresort.data.models.Song


class SongsAdapter(
    val context:Context,
    val arrayList: List<Song>
) : RecyclerView.Adapter<SongsAdapter.DataObjectHolder>() {


    class DataObjectHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var name: AppCompatTextView? = itemView?.findViewById(R.id.name)
        var artist: AppCompatTextView? = itemView?.findViewById(R.id.artist)
        var genre: AppCompatTextView? = itemView?.findViewById(R.id.genre)

    }


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): DataObjectHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return DataObjectHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DataObjectHolder, position: Int) {

        val item = arrayList[position]

        holder.name?.text = item.title
        holder.artist?.text = context.getString(R.string.artist, item.artist);
        holder.genre?.text = context.getString(R.string.genre, item.genre);
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


}