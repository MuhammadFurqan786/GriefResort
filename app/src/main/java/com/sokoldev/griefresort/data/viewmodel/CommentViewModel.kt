package com.sokoldev.griefresort.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sokoldev.griefresort.data.models.Comment

class CommentViewModel : ViewModel() {


    private val list = MutableLiveData<List<Comment>>()

    fun getList(): LiveData<List<Comment>> = list

    var arrayList = ArrayList<Comment>()

    init {


        arrayList.add(
            Comment("Sheila", "Sorry to hear this")
        )
        arrayList.add(
            Comment("Sheila", "Sorry to hear this")
        )
        arrayList.add(
            Comment("Sheila", "Sorry to hear this")
        )
        arrayList.add(
            Comment("Sheila", "Sorry to hear this")
        )
        arrayList.add(
            Comment("Sheila", "Sorry to hear this")
        )
        arrayList.add(
            Comment("Sheila", "Sorry to hear this")
        )

        list.postValue(arrayList)
    }
}