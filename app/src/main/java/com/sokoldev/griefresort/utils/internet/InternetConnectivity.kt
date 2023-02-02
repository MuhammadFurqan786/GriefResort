package com.sokoldev.griefresort.utils.internet

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import androidx.annotation.RequiresPermission
import android.net.NetworkInfo


object InternetConnectivity {
    @RequiresPermission(value = Manifest.permission.ACCESS_NETWORK_STATE)
    fun isConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        connectivityManager?.let {
            val netInfo = it.activeNetworkInfo
            netInfo?.let {
                if (it.isConnected) return true
            }
        }
        return false
    }
}