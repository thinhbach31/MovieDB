package com.example.moviedb.screen.main

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.moviedb.R
import com.example.moviedb.base.BaseActivity
import com.example.moviedb.broastcast.MainNetworkReceiver
import com.example.moviedb.viewmodel.factory.MainVMFactory
import com.example.moviedb.viewmodel.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var networkReceiver: MainNetworkReceiver
    private var viewModel: MainViewModel? = null

    fun showNetworkStatus() {
        text_main_network_status.visibility = View.VISIBLE
    }

    fun hideNetworkStatus() {
        text_main_network_status.visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MainVMFactory().also {
            viewModel = ViewModelProviders.of(this, it).get(MainViewModel::class.java)
        }
        observe()

        initNetworkReceiver()

        MainFragment().apply {
            (this@MainActivity).addFragmentWithoutAddToStack(R.id.container_main, this)
        }
    }

    private fun observe() {
        viewModel?.eventLoading?.observe(this, Observer {
            if (it == true) {
                showLoading()
            } else {
                hideLoading()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        unRegisterMainReceiver()
    }

    private fun initNetworkReceiver() {
        networkReceiver = MainNetworkReceiver(this)
        registerMainReceiver()
    }

    private fun registerMainReceiver() {
        registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun unRegisterMainReceiver() {
        unregisterReceiver(networkReceiver)
    }

    override fun showLoading() {
        main_loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        main_loading.visibility = View.GONE
    }
}
