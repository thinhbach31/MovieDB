package com.example.moviedb.model.body

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MoviesBody(
    @SerializedName("api_key")
    @Expose
    var api_key: String,

    @SerializedName("language")
    @Expose
    var language: String,

    @SerializedName("page")
    @Expose
    var page: Int
) {
}