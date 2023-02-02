package com.sokoldev.griefresort.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sokoldev.griefresort.data.models.Diary

class DiaryViewModel : ViewModel() {


    private val list = MutableLiveData<List<Diary>>()

    fun getList(): LiveData<List<Diary>> = list

    private var arrayList = ArrayList<Diary>()
    private var arrayListComment = ArrayList<Diary.Comments>()

    init {
        for (i in 1..3) {
            arrayListComment.add(
                Diary.Comments(
                    "Shiela",
                    "Very sorry for the loss of your dear\n" +
                            "mother. Please be kind to yourself..\n" +
                            "mom would want you to. It takes \n" +
                            "time..just give yourself permission \n" +
                            "to “feel.” One day at a time. They \n" +
                            "are very helpful. May God keep you \n" +
                            "close. \uD83D\uDC99",
                    "10:05 pm"

                )
            )
        }
        for (i in 1..5) {
            arrayList.add(
                Diary(
                    "Sheron",
                    "09/10/2022",
                    "All we are left with now are memories. Let\\'s keep those memories alive forever. Here add photos/videos of your loved ones, the funeral, grave visits, their treasured items",
                    "50",
                    "20",
                    arrayListComment
                )
            )
        }
        list.postValue(arrayList)
    }
}