package com.example.moviedb.model.entry

import com.google.gson.annotations.SerializedName

data class GenreEntry(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
) {
}