package com.sokoldev.griefresort.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sokoldev.griefresort.data.models.RemindMe

class RemindViewModel : ViewModel() {


    private val list = MutableLiveData<List<RemindMe>>()

    fun getList(): LiveData<List<RemindMe>> = list

    var arrayList = ArrayList<RemindMe>()

    init {


        for (i in 1..5) {
            arrayList.add(
                RemindMe(
                    "Dad's 5th Anniversary",
                    "08.01.2022"
                )
            )
            list.postValue(arrayList)
        }
    }
}