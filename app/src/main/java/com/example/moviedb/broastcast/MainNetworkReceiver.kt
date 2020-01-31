package com.example.moviedb.broastcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast

class MainNetworkReceiver: BroadcastReceiver() {

    private var capabilities: NetworkCapabilities? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        if (isOnline(context!!)) {
            Toast.makeText(context, "online", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "offline", Toast.LENGTH_SHORT).show()
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