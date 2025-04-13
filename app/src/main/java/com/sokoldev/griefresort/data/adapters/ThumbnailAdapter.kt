package com.sokoldev.griefresort.data.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sokoldev.griefresort.R

class ThumbnailAdapter(
    private val context: Context,
    private val mediaList: List<String>,
    private val fileType: String,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<ThumbnailAdapter.ThumbnailViewHolder>() {

    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbnailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_thumbnail, parent, false)
        return ThumbnailViewHolder(view)
    }

    override fun onBindViewHolder(holder: ThumbnailViewHolder, position: Int) {
        holder.bind(mediaList[position], position)
    }

    override fun getItemCount(): Int = mediaList.size

    inner class ThumbnailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val thumbnailImage: ImageView = view.findViewById(R.id.thumbnailImageView)

        fun bind(url: String, position: Int) {
            if (fileType == "image") {
                Glide.with(context).load(url).into(thumbnailImage)
            } else {
                Glide.with(context).load(url) // Load video/audio thumbnail (can be enhanced)
                    .placeholder(R.drawable.ic_video)
                    .into(thumbnailImage)
            }

            thumbnailImage.alpha = if (position == selectedPosition) 1.0f else 0.5f

            itemView.setOnClickListener {
                onItemClick(position)
            }
        }
    }

    fun setSelectedPosition(position: Int) {
        selectedPosition = position
        notifyDataSetChanged()
    }
}
