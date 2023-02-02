package com.sokoldev.griefresort.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sokoldev.griefresort.data.models.GroupHug

class GroupHugViewModel : ViewModel() {


    private val list = MutableLiveData<List<GroupHug>>()

    fun getList(): LiveData<List<GroupHug>> = list

    var arrayList = ArrayList<GroupHug>()

    init {
        for (i in 1..5) {
            arrayList.add(GroupHug("Sheron",
                "09/10/2022",
                "All we are left with now are memories. Let\\'s keep those memories alive forever. Here add photos/videos of your loved ones, the funeral, grave visits, their treasured items",
                "50",
                "20"))
        }
        list.postValue(arrayList)
    }
}