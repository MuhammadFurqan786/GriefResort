package com.sokoldev.griefresort.data.models

data class TVShow(
    val title: String,
    val summary: String,
    val genre: List<String>,
    val main_actors: List<String>,
    val director: String,
    val where_to_watch: List<String>
)
