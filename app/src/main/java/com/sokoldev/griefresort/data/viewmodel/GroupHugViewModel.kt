package com.sokoldev.griefresort.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sokoldev.griefresort.data.models.GroupHug

class GroupHugViewModel : ViewModel() {


    private val list = MutableLiveData<List<GroupHug>>()
    private val hugs = MutableLiveData<Int>()

    fun getHugs (): LiveData<Int> = hugs

    fun getList(): LiveData<List<GroupHug>> = list


    var arrayList = ArrayList<GroupHug>()

    init {
        arrayList.add(
            GroupHug(
                "Sheron186",
                "20/02/2023",
                "I just want my mom back.. next month makes a year without her and i still can’t believe it. I’m struggling so hard \uD83D\uDE2D\uD83D\uDE2D i feel like i have no one.",
                "290",
                "20"
            )
        )
        arrayList.add(
            GroupHug(
                "David",
                "21/02/2023",
                "Why did my mom have to die she was only 58 yrs old she was my only cheerleader I miss her so much she wanted nothing more then to see her grandson's graduate high school and such why did God rob me this why? My heart hurts \uD83D\uDC94 \uD83D\uDE1E she's is missing so much she didn't have to die there are so many people much older than her still alive. It's not getting much easier people lie to you when they say it will. I will never see her or talk to her again I still angry and can't understand why my mind is so bad I can't remember anything anymore \uD83D\uDE15 \uD83D\uDE2A. I Need her \uD83D\uDC94",
                "500",
                "39"
            )
        )

        arrayList.add(
            GroupHug(
                "Diana N",
                "23/02/2023",
                "Since I lost my dad last year, I’ve been really depressed I cut everyone out of my life even my family, I lost all of my motivation to pursue anything. I even quit my 6 figure job that I spent 2 years trying to get hired at. I’m simply existing without a purpose…",
                "120",
                "10"
            )
        )


        list.postValue(arrayList)
    }


}