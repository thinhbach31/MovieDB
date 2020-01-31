package com.example.moviedb.viewmodel.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.moviedb.BuildConfig
import com.example.moviedb.api.APIBuilder
import com.example.moviedb.base.BaseViewModel
import com.example.moviedb.base.StringUtils
import com.example.moviedb.model.body.MoviesBody
import com.example.moviedb.model.entry.ListMoviesEntry
import com.example.moviedb.viewmodel.repository.MainVMRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : BaseViewModel() {

    private val disposables = CompositeDisposable()

    private var listMoviesLiveData = MutableLiveData<ListMoviesEntry>()

    fun getListMoviesResponse(): MutableLiveData<ListMoviesEntry> {
        return this.listMoviesLiveData
    }

    fun setListMovieResponse(listMoviesEntry: ListMoviesEntry) {
        this.listMoviesLiveData.value = listMoviesEntry
    }

    fun getListMovies(pageNum: Int) {
        val moviesBody = MoviesBody(BuildConfig.API_KEY, StringUtils.DEFAULT_LANGUAGE, pageNum)
//        MainVMRepository.also {
//            it.getListMovies(disposables, moviesBody, this)
//        }
        disposables.add(
            APIBuilder.getWebService().getPopularMovies(
                moviesBody.api_key,
                moviesBody.language,
                moviesBody.page
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    showLoading(true)
                }
                .doFinally {
                    showLoading(false)
                }
                .subscribe({
                    setListMovieResponse(it)
                }, {
                    showFailure(it)
                })

        )
    }
}