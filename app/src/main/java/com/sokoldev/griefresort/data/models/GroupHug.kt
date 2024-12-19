package com.sokoldev.griefresort.data.models

data class GroupHug(
    var id: String? = null,
    var userId :String? = null,
    var userName: String? = null,
    var date: String? = null,
    var description: String? = null,
    var totalHugs: Int? = null,
    var totalComments: Int? = null,
    var comments: List<Comment>? = null
)

