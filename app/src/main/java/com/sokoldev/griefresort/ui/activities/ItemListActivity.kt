package com.sokoldev.griefresort.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sokoldev.griefresort.data.adapters.BooksAdapter
import com.sokoldev.griefresort.data.adapters.MovieAdapter
import com.sokoldev.griefresort.data.adapters.PodcastAdapter
import com.sokoldev.griefresort.data.adapters.SongsAdapter
import com.sokoldev.griefresort.data.adapters.TvShowAdapter
import com.sokoldev.griefresort.data.models.TVShow
import com.sokoldev.griefresort.data.viewmodel.ItemViewModel
import com.sokoldev.griefresort.databinding.ActivityItemListBinding
import com.sokoldev.griefresort.utils.Constants

class ItemListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemListBinding
    private lateinit var itemViewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itemViewModel = ViewModelProvider(this)[ItemViewModel::class.java]

        binding.rvItem.layoutManager = LinearLayoutManager(this)
        binding.rvItem.setHasFixedSize(true)

        val intent = intent
        val item = intent.getStringExtra(Constants.ITEM)

        if (!item.equals(null)) {
            if (item.equals(Constants.BOOKS)) {
                binding.selectedListText.text = "Books List"
                binding.chooseText.text = "Choose Your Book"
                itemViewModel.loadBooks()
                setObserverBooks()
            } else if (item.equals(Constants.MOVIES)) {
                binding.selectedListText.text = "Movies List"
                binding.chooseText.text = "Choose Your Movie"
                itemViewModel.laodMovies()
                setObserverMovies()

            } else if (item.equals(Constants.PODCASTS)) {
                binding.selectedListText.text = "Podcasts List"
                binding.chooseText.text = "Choose Your Podcast"
                itemViewModel.loadPodcasts()
                setObserverPodcasts()
            } else if (item.equals(Constants.TVSHOWS)) {
                binding.selectedListText.text = "Tv Shows List"
                binding.chooseText.text = "Choose Your Tv Show"
                itemViewModel.loadShows()
                setObserverShows()
            } else if (item.equals(Constants.SONGS)) {
                binding.selectedListText.text = "Songs List"
                binding.chooseText.text = "Choose Your Song"
                itemViewModel.loadSongs()
                setObserverSongs()
            }
        }

        binding.back.setOnClickListener {
            finish()
        }

    }

    private fun setObserverShows() {
        itemViewModel.shows.observe(this, Observer {
            val adapter = TvShowAdapter(this, it)
            binding.rvItem.adapter = adapter
        })
    }

    private fun setObserverPodcasts() {
        itemViewModel.podcast.observe(this, Observer {
            val adapter = PodcastAdapter(this, it)
            binding.rvItem.adapter = adapter
        })
    }

    private fun setObserverMovies() {
        itemViewModel.movies.observe(this, Observer {
            val adapter = MovieAdapter(this, it)
            binding.rvItem.adapter = adapter
        })
    }


    // getting songs from assets
    private fun setObserverSongs() {
        itemViewModel.songs.observe(this, Observer {
            val adapter = SongsAdapter(this, it)
            binding.rvItem.adapter = adapter
        })
    }

    // getting books from assets
    private fun setObserverBooks() {
        itemViewModel.books.observe(this, Observer {
            val adapter = BooksAdapter(this, it)
            binding.rvItem.adapter = adapter
        })
    }
}