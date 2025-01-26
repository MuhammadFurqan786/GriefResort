package com.sokoldev.griefresort.data.models

data class Movie(
    val title: String,
    val summary: String,
    val genre: String,
    val main_actors: List<String>,
    val director: String
)
