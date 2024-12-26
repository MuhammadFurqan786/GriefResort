package com.sokoldev.griefresort.data.models

import com.google.gson.annotations.SerializedName

 data class BookResponse (
     @SerializedName("books")
     var books: List<Book>? = null
 )
