package com.sokoldev.griefresort.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.models.Library
import com.sokoldev.griefresort.utils.Constants

class LibraryViewModel : ViewModel() {


    private val list = MutableLiveData<List<Library>>()

    fun getList(): LiveData<List<Library>> = list

    var arrayList = ArrayList<Library>()

    init {


        arrayList.add(
            Library(
                R.drawable.ic_book,
                Constants.BOOKS
            )
        )

        arrayList.add(
            Library(
                R.drawable.ic_video,
                Constants.MOVIES
            )
        )
        arrayList.add(
            Library(
                R.drawable.ic_tv_show,
                Constants.TVSHOWS
            )
        )
        arrayList.add(
            Library(
                R.drawable.ic_podcast,
                Constants.PODCASTS
            )
        )


        arrayList.add(
            Library(
                R.drawable.ic_music,
                Constants.SONGS
            )
        )

        arrayList.add(
            Library(
                R.drawable.ic_quotes,
                Constants.QUOTES
            )
        )

        list.postValue(arrayList)
    }
}