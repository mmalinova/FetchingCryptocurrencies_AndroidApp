package com.softuni.mobileexam.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object Network {

    fun isConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            return if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                true
            } else capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        }
        return false
    }
}