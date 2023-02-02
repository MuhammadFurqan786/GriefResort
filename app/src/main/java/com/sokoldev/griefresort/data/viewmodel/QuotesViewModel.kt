package com.sokoldev.griefresort.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sokoldev.griefresort.data.models.Quote

class QuotesViewModel : ViewModel() {


    private val list = MutableLiveData<List<Quote>>()

    fun getList(): LiveData<List<Quote>> = list

    var arrayList = ArrayList<Quote>()

    init {


        for (i in 1..5) {
            arrayList.add(
                Quote(
                    "The saddest part of life is when the person who gave you the best memories becomes a memory ",
                    "Unknown"
                )
            )

            list.postValue(arrayList)
        }
    }
}