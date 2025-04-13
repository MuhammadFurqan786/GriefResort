package com.sokoldev.griefresort.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.adapters.MediaPagerAdapter
import com.sokoldev.griefresort.data.adapters.ThumbnailAdapter
import com.sokoldev.griefresort.utils.Constants

class MediaPlayerActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var thumbnailRecyclerView: RecyclerView
    private lateinit var thumbnailAdapter: ThumbnailAdapter
    private lateinit var closeButton: ImageView
    private var mediaList: ArrayList<String>? = null
    private var fileType: String? = null
    private var startPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_player)

        viewPager = findViewById(R.id.viewPager)
        thumbnailRecyclerView = findViewById(R.id.thumbnailRecyclerView)
        closeButton = findViewById(R.id.closeButton)
        closeButton.setOnClickListener { finish() }

        mediaList = intent.getStringArrayListExtra(Constants.MEDIA_URL_LIST)
        fileType = intent.getStringExtra(Constants.FILE_TYPE)
        startPosition = intent.getIntExtra(Constants.START_POSITION, 0)

        if (!mediaList.isNullOrEmpty()) {
            viewPager.adapter = MediaPagerAdapter(this, mediaList!!, fileType!!, startPosition)
            viewPager.setCurrentItem(startPosition, false)
            setupThumbnails()
            syncViewPagerWithThumbnails()
        }
    }

    private fun setupThumbnails() {
        thumbnailAdapter = ThumbnailAdapter(this, mediaList!!, fileType!!) { position ->
            // Release previous player before selecting a new one
            (viewPager.adapter as? MediaPagerAdapter)?.releaseActivePlayer()

            // Move to selected item
            viewPager.setCurrentItem(position, true)

            // Force start playing
            (viewPager.adapter as? MediaPagerAdapter)?.notifyItemChanged(position)
        }

        thumbnailRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MediaPlayerActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = thumbnailAdapter
        }
    }


    private fun syncViewPagerWithThumbnails() {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val adapter = viewPager.adapter as? MediaPagerAdapter

                // Release previous media player
                adapter?.releaseActivePlayer()

                // Notify adapter that the new item should play
                adapter?.notifyItemChanged(position)

                // Scroll the thumbnails
                thumbnailAdapter.setSelectedPosition(position)
                thumbnailRecyclerView.smoothScrollToPosition(position)
            }
        })
    }



    override fun onPause() {
        super.onPause()
        (viewPager.adapter as? MediaPagerAdapter)?.releaseActivePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        (viewPager.adapter as? MediaPagerAdapter)?.releaseActivePlayer()
    }
}

