package com.sokoldev.griefresort.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.models.MemoryBox

class MemoryViewModel : ViewModel() {


    private val list = MutableLiveData<List<MemoryBox>>()

    fun getList(): LiveData<List<MemoryBox>> = list

    var arrayList = ArrayList<MemoryBox>()

    init {


        arrayList.add(
            MemoryBox(
                R.drawable.img,
            )
        )

        arrayList.add(
            MemoryBox(
                R.drawable.img3,
            )
        )

        arrayList.add(
            MemoryBox(
                R.drawable.img2,
            )
        )

        arrayList.add(
            MemoryBox(
                R.drawable.img,
            )
        )

        arrayList.add(
            MemoryBox(
                R.drawable.img2,
            )
        )

        list.postValue(arrayList)
    }
}