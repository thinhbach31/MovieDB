package com.example.moviedb.api


import com.example.moviedb.model.entry.ListMoviesEntry
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    // *total

    //popular
    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String,
                         @Query("language") language: String,
                         @Query("page") page: Int): Observable<ListMoviesEntry>

    //top rate
    @GET("movie/top_rated")
    fun getTopRateMovies(@Query("api_key") apiKey: String,
                         @Query("language") language: String,
                         @Query("page") page: Int): Observable<ListMoviesEntry>

    //now playing
    @GET("movie/now_playing")
    fun getNowPlayingMovies(@Query("api_key") apiKey: String,
                         @Query("language") language: String,
                         @Query("page") page: Int): Observable<ListMoviesEntry>

    //upcoming
    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query("api_key") apiKey: String,
                         @Query("language") language: String,
                         @Query("page") page: Int): Observable<ListMoviesEntry>

    //genre
    // /genre/movie/list?api_key=9939d81cc8f867e5efc8e9094f1e2b40&language=en-US

    //by genre
    // /discover/movie?api_key=9939d81cc8f867e5efc8e9094f1e2b40&with_genres=28

    // *detail

    // detail
    ///movie/{movie_id}?api_key=9939d81cc8f867e5efc8e9094f1e2b40&language=en-US

    //review
    // /movie/299536/reviews?api_key=9939d81cc8f867e5efc8e9094f1e2b40&language=en-US&page=1

    //video
    // /movie/299536/videos?api_key=9939d81cc8f867e5efc8e9094f1e2b40&language=en-US
}