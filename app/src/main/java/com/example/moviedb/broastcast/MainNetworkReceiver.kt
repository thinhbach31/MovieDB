package com.example.moviedb.broastcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.moviedb.screen.main.MainActivity

class MainNetworkReceiver(private val activity: MainActivity) : BroadcastReceiver() {

    private var capabilities: NetworkCapabilities? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        if (isOnline(context!!)) {
            activity.hideNetworkStatus()

        } else {
            activity.showNetworkStatus()
        }
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)!!) {
                return true
            }

        } else {

            try {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo;
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                    return true
                }
            } catch (e: Exception) {
                return false
            }
        }
        return false
    }
}