package com.sokoldev.griefresort.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comment(
    var commentId: String? = null,
    var userId: String? = null,
    var userName: String? = null,
    var comment: String? = null,
    var timestamp: Long? = null
) : Parcelable