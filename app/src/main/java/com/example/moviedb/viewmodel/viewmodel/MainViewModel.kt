package com.example.moviedb.viewmodel.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.moviedb.BuildConfig
import com.example.moviedb.base.BaseViewModel
import com.example.moviedb.base.StringUtils
import com.example.moviedb.model.body.MoviesBody
import com.example.moviedb.model.entry.ListMoviesEntry
import com.example.moviedb.model.entry.Result
import com.example.moviedb.viewmodel.repository.MainVMRepository
import io.reactivex.disposables.CompositeDisposable

class MainViewModel : BaseViewModel() {

    private val disposables = CompositeDisposable()

    private var popularMoviesLiveData = MutableLiveData<ListMoviesEntry>()
    private var nowPlayingMoviesLiveData = MutableLiveData<ListMoviesEntry>()
    private var topRateMoviesLiveData = MutableLiveData<ListMoviesEntry>()
    private var upcomingMoviesLiveData = MutableLiveData<ListMoviesEntry>()

    fun getPopularMoviesResponse(): MutableLiveData<ListMoviesEntry> {
        return this.popularMoviesLiveData
    }

    fun setPopularMovieResponse(listMoviesEntry: ListMoviesEntry) {
        this.popularMoviesLiveData.value = listMoviesEntry
    }

    fun getNowPlayingMoviesResponse(): MutableLiveData<ListMoviesEntry> {
        return this.nowPlayingMoviesLiveData
    }

    fun setNowPlayingMovieResponse(listMoviesEntry: ListMoviesEntry) {
        this.nowPlayingMoviesLiveData.value = listMoviesEntry
    }

    fun getTopRateMoviesResponse(): MutableLiveData<ListMoviesEntry> {
        return this.topRateMoviesLiveData
    }

    fun setTopRateMovieResponse(listMoviesEntry: ListMoviesEntry) {
        this.topRateMoviesLiveData.value = listMoviesEntry
    }

    fun getUpcomingMoviesResponse(): MutableLiveData<ListMoviesEntry> {
        return this.upcomingMoviesLiveData
    }

    fun setUpcomingMovieResponse(listMoviesEntry: ListMoviesEntry) {
        this.upcomingMoviesLiveData.value = listMoviesEntry
    }

    fun getMovies(pageNum: Int, type: String) {
        val moviesBody = MoviesBody(BuildConfig.API_KEY, StringUtils.DEFAULT_LANGUAGE, pageNum)
        MainVMRepository.also {
            it.getMovies(disposables, moviesBody, this, type)
        }
    }

    fun clearData() {
        popularMoviesLiveData = MutableLiveData<ListMoviesEntry>()
        nowPlayingMoviesLiveData = MutableLiveData<ListMoviesEntry>()
        topRateMoviesLiveData = MutableLiveData<ListMoviesEntry>()
        upcomingMoviesLiveData = MutableLiveData<ListMoviesEntry>()
    }
}