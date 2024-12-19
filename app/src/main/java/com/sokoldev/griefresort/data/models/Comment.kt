package com.sokoldev.griefresort.data.models

import android.os.Parcel
import android.os.Parcelable

data class Comment(
    var commentId: String? = null,
    var userId: String? = null,
    var userName: String? = null,
    var comment: String? = null,
    var timestamp: Long? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(commentId)
        parcel.writeString(userId)
        parcel.writeString(userName)
        parcel.writeString(comment)
        parcel.writeValue(timestamp)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Comment> {
        override fun createFromParcel(parcel: Parcel): Comment {
            return Comment(parcel)
        }

        override fun newArray(size: Int): Array<Comment?> {
            return arrayOfNulls(size)
        }
    }
}
