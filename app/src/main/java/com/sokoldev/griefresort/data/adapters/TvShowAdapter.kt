package com.sokoldev.griefresort.data.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.models.TVShow

class TvShowAdapter(val context: Context, private val movies: List<TVShow>) :
    RecyclerView.Adapter<TvShowAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie,context)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: AppCompatTextView = itemView.findViewById(R.id.name)
        private val imageview: AppCompatImageView = itemView.findViewById(R.id.image)
        private val summaryTextView: AppCompatTextView = itemView.findViewById(R.id.summary)
        private val genreTextView: AppCompatTextView = itemView.findViewById(R.id.genre)
        private val actorsTextView: AppCompatTextView = itemView.findViewById(R.id.actors)
        private val directorTextView: AppCompatTextView = itemView.findViewById(R.id.director)
        private val watchTextView: AppCompatTextView = itemView.findViewById(R.id.watch)

        fun bind(movie: TVShow, context: Context) {
            imageview.setImageResource(R.drawable.ic_tv_show)
            titleTextView.text = movie.title
            summaryTextView.text = movie.summary
            genreTextView.text = context.getString(R.string.genre, movie.genre.joinToString(  "," ));
            actorsTextView.text = context.getString(R.string.actors, movie.main_actors.joinToString(", "))
            watchTextView.text =
                context.getString(R.string.where_to_watch, movie.where_to_watch.joinToString(", "))
            directorTextView.text = context.getString(R.string.director, movie.director)

            watchTextView.visibility = View.VISIBLE
        }
    }
}
