package com.example.moviedb.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class APIBuilder {
    companion object {
        private val apiInterface: APIService? = null
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        fun getWebService(): APIService {
            if (apiInterface != null) {
                return apiInterface
            }
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return retrofit.create(APIService::class.java)
        }
    }
}