package com.example.moviedb.listener

import com.example.moviedb.model.entry.Result

interface MovieItemClickListener {
    fun onItemClick(result: Result)
}