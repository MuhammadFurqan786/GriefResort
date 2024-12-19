package com.sokoldev.griefresort.data.models

data class User(
    val userId:String?=null,
    val firstName: String? = null,
    val lastName: String? = null,
    val userName: String? = null,
    val email: String? = null,
    val fcmToken: String? = null,
    val isPremium: Boolean = false,
    val trialStartDate: Long? = null,
    val trialEndDate: Long? = null,
    var reminders: List<RemindMe> = emptyList()
)
