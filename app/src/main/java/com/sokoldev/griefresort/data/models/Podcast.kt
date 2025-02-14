package com.sokoldev.griefresort.data.models

data class Podcast(
    val title: String,
    val summary: String,
    val genre: List<String>,
    val host: String,
    val where_to_listen: List<String>
)
