package com.sokoldev.griefresort.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.bumptech.glide.Glide
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.utils.Constants

class MediaPlayerActivity : AppCompatActivity() {

    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView
    private lateinit var imageView: ImageView
    private lateinit var closeButton: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_media_player)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        playerView = findViewById(R.id.player_view)
        imageView = findViewById(R.id.imageView)
        closeButton = findViewById(R.id.closeButton)


        // Initialize ExoPlayer
        player = ExoPlayer.Builder(this).build()
        playerView.player = player

        // Get the media URL passed from the previous activity
        val mediaUrl = intent.getStringExtra(Constants.MEDIA_URL)
        val fileType = intent.getStringExtra(Constants.FILE_TYPE)

        if (mediaUrl.isNullOrEmpty()) {
            Toast.makeText(this, "Invalid media URL", Toast.LENGTH_SHORT).show()
            return
        }

        // Check if the URL is an image, video, or audio
        if (fileType.equals("image")) {
            // Load image using Glide
            imageView.visibility = View.VISIBLE
            playerView.visibility = View.GONE
            Glide.with(this)
                .load(mediaUrl)
                .into(imageView)
        } else {
            // Media is video or audio, use ExoPlayer
            imageView.visibility = View.GONE
            playerView.visibility = View.VISIBLE
            val mediaItem = MediaItem.fromUri(mediaUrl)

            // Prepare the player with the media item
            player.setMediaItem(mediaItem);
            player.prepare();
            player.playWhenReady = true; // Start playing when ready
        }

        // Close button logic
        closeButton.setOnClickListener {
            finish()  // Close the activity when the close button is clicked
        }
    }

    override fun onPause() {
        super.onPause()
        player.release()  // Release player when the activity is paused or stopped
    }

    override fun onStop() {
        super.onStop()
        if (player.isPlaying) {
            player.pause()  // Pause playback when the activity is stopped
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()  // Clean up the player
    }
}