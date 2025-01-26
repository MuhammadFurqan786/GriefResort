package com.sokoldev.griefresort.data.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.models.Podcast

class PodcastAdapter(val context: Context, private val movies: List<Podcast>) :
    RecyclerView.Adapter<PodcastAdapter.PodCastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodCastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return PodCastViewHolder(view)
    }

    override fun onBindViewHolder(holder: PodCastViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie, context)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class PodCastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: AppCompatTextView = itemView.findViewById(R.id.name)
        private val imageView: AppCompatImageView = itemView.findViewById(R.id.image)
        private val summaryTextView: AppCompatTextView = itemView.findViewById(R.id.summary)
        private val genreTextView: AppCompatTextView = itemView.findViewById(R.id.genre)
        private val actorsTextView: AppCompatTextView = itemView.findViewById(R.id.actors)
        private val directorTextView: AppCompatTextView = itemView.findViewById(R.id.director)
        private val watchTextView: AppCompatTextView = itemView.findViewById(R.id.watch)

        fun bind(movie: Podcast, context: Context) {
            imageView.setImageResource(R.drawable.ic_podcast)
            titleTextView.text = movie.title
            summaryTextView.text = movie.summary
            genreTextView.text = context.getString(R.string.genre, movie.genre.joinToString(", "))
            actorsTextView.text = context.getString(R.string.host, movie.host)
            directorTextView.text =
                context.getString(R.string.where_to_listen, movie.whereToListen.joinToString(","))
        }
    }
}
