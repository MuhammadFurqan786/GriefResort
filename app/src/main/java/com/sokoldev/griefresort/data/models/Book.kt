package com.sokoldev.griefresort.data.models

import com.google.gson.annotations.SerializedName

data class Book(
    @SerializedName("title")
    val title: String?=null,
    @SerializedName("author")
    val author: String?=null,
    @SerializedName("summary")
    val summary: String?=null
)