package com.sokoldev.griefresort.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.models.Item

class ItemViewModel : ViewModel() {


    private val listMovies = MutableLiveData<List<Item>>()
    private val listBooks = MutableLiveData<List<Item>>()
    private val listShows = MutableLiveData<List<Item>>()
    private val listSongs = MutableLiveData<List<Item>>()
    private val listPodCasts = MutableLiveData<List<Item>>()

    fun getListBooks(): LiveData<List<Item>> = listBooks
    fun getListTvShows(): LiveData<List<Item>> = listShows
    fun getListPodCast(): LiveData<List<Item>> = listSongs
    fun getListSongs(): LiveData<List<Item>> = listPodCasts
    fun getListMovies(): LiveData<List<Item>> = listMovies

    var arrayListBooks = ArrayList<Item>()
    var arrayListTvShows = ArrayList<Item>()
    var arrayListPodCasts = ArrayList<Item>()
    var arrayListSongs = ArrayList<Item>()
    var arrayListMovies = ArrayList<Item>()

    init {


        for (i in 1..5) {
            arrayListBooks.add(
                Item(
                    R.drawable.img,
                    "Book $i",
                )
            )
        }
        listBooks.postValue(arrayListBooks)

        for (i in 1..5) {
            arrayListMovies.add(
                Item(
                    R.drawable.img,
                    "Movie $i",
                )
            )
        }
        listMovies.postValue(arrayListMovies)

        for (i in 1..5) {
            arrayListTvShows.add(
                Item(
                    R.drawable.img,
                    "Tv Shows $i",
                )
            )
        }
        listShows.postValue(arrayListTvShows)

        for (i in 1..5) {
            arrayListSongs.add(
                Item(
                    R.drawable.img,
                    "Song $i",
                )
            )
        }
        listSongs.postValue(arrayListSongs)

        for (i in 1..5) {
            arrayListPodCasts.add(
                Item(
                    R.drawable.img,
                    "PodCast $i",
                )
            )
        }
        listPodCasts.postValue(arrayListPodCasts)
    }
}