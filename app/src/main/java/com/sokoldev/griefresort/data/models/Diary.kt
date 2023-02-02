package com.sokoldev.griefresort.data.models


data class Diary(
    var userName: String,
    var date: String,
    var description: String,
    var totalHugs: String,
    var totalComments: String,
    var listComments: List<Comments>
) {
    data class Comments(
        var name: String,
        var comment: String,
        var time: String
    )

}

