package com.sokoldev.griefresort.data.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.models.Book
import com.sokoldev.griefresort.data.models.Item
import com.sokoldev.griefresort.data.models.Quote
import com.sokoldev.griefresort.data.models.Song
import com.sokoldev.griefresort.data.repository.BookRepository
import com.sokoldev.griefresort.data.repository.QuoteRepository
import com.sokoldev.griefresort.data.repository.SongRepository

class ItemViewModel(application: Application) : AndroidViewModel(application) {

    private val bookRepository: BookRepository = BookRepository(application.applicationContext)
    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> get() = _books

    private val quoteRepository: QuoteRepository = QuoteRepository(application.applicationContext)
    private val _quotes = MutableLiveData<List<Quote>>()
    val quotes: LiveData<List<Quote>> get() = _quotes


    private val songRepository: SongRepository = SongRepository(application.applicationContext)
    private val _songs = MutableLiveData<List<Song>>()
    val songs: LiveData<List<Song>> get() = _songs



    fun loadBooks() {
        val response = bookRepository.getBooksFromAssets()
        response?.books.let {
            _books.postValue(it)
            Log.d("BOOKS" , it?.size.toString())
        }
    }

    fun loadSongs() {
        val response = songRepository.getSongsFromAssets()
        response.let {
            _songs.postValue(it)
            Log.d("Songs" , it.size.toString())
        }
    }


    fun loadQuotes() {
        val response = quoteRepository.getQuotesFromAssets()
        response.let {
            _quotes.postValue(it)
            Log.d("QUOTES" , it.size.toString())
        }
    }

}