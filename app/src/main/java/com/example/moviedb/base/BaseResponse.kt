package com.example.moviedb.base

data class BaseResponse(
    var title: String? = null,
    var message: String? = null,
    var request_code: String? = null
)