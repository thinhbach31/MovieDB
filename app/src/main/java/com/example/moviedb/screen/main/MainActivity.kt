package com.example.moviedb.screen.main

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import com.example.moviedb.R
import com.example.moviedb.base.BaseActivity
import com.example.moviedb.broastcast.MainNetworkReceiver

class MainActivity : BaseActivity() {

    private lateinit var networkReceiver: MainNetworkReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNetworkReceiver()

        MainFragment().apply {
            addFragmentWithoutAddToStack(R.id.container_main, this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unRegisterMainReceiver()
    }

    private fun initNetworkReceiver(){
        networkReceiver = MainNetworkReceiver()
        registerMainReceiver()
    }

    private fun registerMainReceiver() {
        registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun unRegisterMainReceiver() {
        unregisterReceiver(networkReceiver)
    }
}
