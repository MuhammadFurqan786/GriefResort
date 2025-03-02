package com.sokoldev.griefresort.data.models

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GroupHug(
    var id: String? = null,
    var userId: String? = null,
    var userName: String? = null,
    var date: String? = null,
    var description: String? = null,
    var totalHugs: Int? = null,
    var totalComments: Int? = null,
    var comments: ArrayList<Comment>? = null,

    @get:PropertyName("isShared") @set:PropertyName("isShared")
    var isShared: Boolean? = false
) : Parcelable
