package com.example.moviedb.screen.main

import android.os.Bundle
import com.example.moviedb.R
import com.example.moviedb.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MainFragment().apply {
            addFragmentWithoutAddToStack(R.id.container_main, this)
        }
    }
}
