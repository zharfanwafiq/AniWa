package com.zharfan.aniwa.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.widget.Toast

object Common {
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun isNetworkEnabled(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        val currentNetwork: Network? = connectivityManager.activeNetwork
        return currentNetwork != null
    }
}