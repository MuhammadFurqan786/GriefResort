package com.sokoldev.griefresort.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sokoldev.griefresort.data.adapters.BooksAdapter
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
//                setObserverMovies()
            } else if (item.equals(Constants.PODCASTS)) {
                binding.selectedListText.text = "Podcasts List"
                binding.chooseText.text = "Choose Your Podcast"
//                setObserverPodcasts()
            } else if (item.equals(Constants.TVSHOWS)) {
                binding.selectedListText.text = "Tv Shows List"
                binding.chooseText.text = "Choose Your Tv Show"
//                setObserverShows()
            } else if (item.equals(Constants.MOVIES)) {
                binding.selectedListText.text = "Songs List"
                binding.chooseText.text = "Choose Your Song"
//                setObserverSong()
            }
        }

        binding.back.setOnClickListener {
            finish()
        }

    }

    // getting books from assets
    private fun setObserverBooks() {
        itemViewModel.books.observe(this, Observer {
            val adapter = BooksAdapter(this, it)
            binding.rvItem.adapter = adapter
        })
    }
}