package com.sokoldev.griefresort.data.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sokoldev.griefresort.data.models.Book
import com.sokoldev.griefresort.data.models.Movie
import com.sokoldev.griefresort.data.models.Podcast
import com.sokoldev.griefresort.data.models.Quote
import com.sokoldev.griefresort.data.models.Song
import com.sokoldev.griefresort.data.models.TVShow
import com.sokoldev.griefresort.data.repository.BookRepository
import com.sokoldev.griefresort.data.repository.MovieRepository
import com.sokoldev.griefresort.data.repository.PodCastRepository
import com.sokoldev.griefresort.data.repository.QuoteRepository
import com.sokoldev.griefresort.data.repository.SongRepository
import com.sokoldev.griefresort.data.repository.TvShowRepository

class ItemViewModel(application: Application) : AndroidViewModel(application) {

    private val bookRepository: BookRepository = BookRepository(application.applicationContext)
    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> get() = _books


    private val movieRepository: MovieRepository = MovieRepository(application.applicationContext)
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    private val quoteRepository: QuoteRepository = QuoteRepository(application.applicationContext)
    private val _quotes = MutableLiveData<List<Quote>>()
    val quotes: LiveData<List<Quote>> get() = _quotes


    private val songRepository: SongRepository = SongRepository(application.applicationContext)
    private val _songs = MutableLiveData<List<Song>>()
    val songs: LiveData<List<Song>> get() = _songs


    private val podCastRepository: PodCastRepository = PodCastRepository(application.applicationContext)
    private val _podcast = MutableLiveData<List<Podcast>>()
    val podcast: LiveData<List<Podcast>> get() = _podcast


    private val tvShowRepository = TvShowRepository(application.applicationContext)
    private val _shows = MutableLiveData<List<TVShow>>()
    val shows: LiveData<List<TVShow>> get() = _shows



    fun loadBooks() {
        val response = bookRepository.getBooksFromAssets()
        response?.books.let {
            _books.postValue(it)
            Log.d("BOOKS" , it?.size.toString())
        }
    }

    fun laodMovies() {
        val response = movieRepository.getMoviesFromAssets()
        response?.movies.let {
            _movies.postValue(it)
            Log.d("Movies", it?.size.toString())
        }
    }

    fun loadSongs() {
        val response = songRepository.getSongsFromAssets()
        response.let {
            _songs.postValue(it)
            Log.d("Songs" , it.size.toString())
        }
    }
    fun loadPodcasts() {
        val response = podCastRepository.getPodcastsFromAssets()
        response.let {
            _podcast.postValue(it)
            Log.d("PodCasts", it.size.toString())
        }
    }

    fun loadShows() {
        val response = tvShowRepository.getShowsFromAssets()
        response.let {
            _shows.postValue(it)
            Log.d("PodCasts", it.size.toString())
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