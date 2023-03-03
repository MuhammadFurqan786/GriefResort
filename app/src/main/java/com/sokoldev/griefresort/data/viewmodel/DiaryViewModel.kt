package com.sokoldev.griefresort.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sokoldev.griefresort.data.models.Diary
import com.sokoldev.griefresort.utils.Constants

class DiaryViewModel : ViewModel() {


    private val list = MutableLiveData<List<Diary>>()

    fun getList(): LiveData<List<Diary>> = list

    private var arrayList = ArrayList<Diary>()
    private var arrayListComment = ArrayList<Diary.Comments>()

    init {
        arrayListComment.add(
            Diary.Comments(
                Constants.OTHERUSERCARD,
                "Shiela",
                "Very sorry for the loss of your dear mother. Please be kind to yourself...mom would want you to. It takes time..just give yourself permission to “feel.” One day at a time. They are very helpful. May God keep you close. \uD83D\uDC99",
                "10:05 pm"

            )
        )

        arrayListComment.add(
            Diary.Comments(
                Constants.USERCARD,
                "",
                "Thank you.",
                "10:05 pm"

            )
        )

        arrayList.add(
            Diary(
                "Sheron",
                "09/10/2022",
                "I can’t stop crying I’m so depressed nothings the same anymore. I just want my mom back but I know it’ll never happen wish this was a bad dream and I’m gonna wake up and my mom would be here. ",
                "50",
                "20",
                arrayListComment
            )
        )
        list.postValue(arrayList)
    }
}