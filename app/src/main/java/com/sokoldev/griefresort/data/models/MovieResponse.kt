package com.sokoldev.griefresort.data.models

import com.google.gson.annotations.SerializedName

 data class MovieResponse (
     @SerializedName("movies")
     var movies: List<Movie>? = null
 )
