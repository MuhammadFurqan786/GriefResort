package com.sokoldev.griefresort.data.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sokoldev.griefresort.R

class MediaPagerAdapter(
    private val context: Context,
    private val mediaList: List<String>,
    private val fileType: String,
    private var startPosition: Int
) : RecyclerView.Adapter<MediaPagerAdapter.MediaViewHolder>() {

    private var activePlayer: ExoPlayer? = null
    private var currentPlayingPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val layoutId = when (fileType) {
            "image" -> R.layout.item_image_pager
            "video", "audio" -> R.layout.item_video_audio
            else -> R.layout.item_image_pager
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return MediaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        val url = mediaList[position]
        holder.bind(url, fileType, position)

        // Auto-play only the initially selected position
        if (position == startPosition) {
            holder.startPlaying()
        }
    }

    override fun getItemCount(): Int = mediaList.size

    inner class MediaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView? = view.findViewById(R.id.imageViewPagerItem)
        private val playerView: PlayerView? = view.findViewById(R.id.player_view)
        private var player: ExoPlayer? = null

        fun bind(url: String, type: String, position: Int) {
            when (type) {
                "image" -> {
                    imageView?.visibility = View.VISIBLE
                    playerView?.visibility = View.GONE
                    Glide.with(context).load(url).into(imageView!!)
                }
                "video", "audio" -> {
                    imageView?.visibility = View.GONE
                    playerView?.visibility = View.VISIBLE
                    setupPlayer(url, playerView, position)
                }
            }
        }

        private fun setupPlayer(url: String, playerView: PlayerView?, position: Int) {
            releaseActivePlayer()

            player = ExoPlayer.Builder(context).build()
            playerView?.player = player
            player?.setMediaItem(MediaItem.fromUri(url))
            player?.prepare()

            // Auto-play only if it's the currently selected item
            if (position == startPosition || position == currentPlayingPosition) {
                player?.playWhenReady = true
            }

            activePlayer = player
            currentPlayingPosition = position
        }

        fun startPlaying() {
            activePlayer?.playWhenReady = true
        }

        fun releasePlayer() {
            player?.release()
            player = null
        }
    }

    fun releaseActivePlayer() {
        activePlayer?.stop()
        activePlayer?.release()
        activePlayer = null
    }
}


