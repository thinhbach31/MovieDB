package com.example.moviedb.viewmodel.repository

import com.example.moviedb.api.APIBuilder
import com.example.moviedb.base.StringUtils
import com.example.moviedb.model.body.MoviesBody
import com.example.moviedb.model.entry.ListMoviesEntry
import com.example.moviedb.viewmodel.viewmodel.MainViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

object MainVMRepository {

    fun getMovies(
        disposable: CompositeDisposable, moviesBody: MoviesBody,
        mainViewModel: MainViewModel, type: String
    ) {
        disposable.add(
            APIBuilder.getWebService().getMovies(
                type, moviesBody.api_key, moviesBody.language, moviesBody.page
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    mainViewModel.showLoading(true)
                }
                .doFinally {
                    mainViewModel.showLoading(false)
                }
                .subscribe({
                    setMoviesResponse(mainViewModel, type, it)
                }, {
                    mainViewModel.showFailure(it)
                })
        )
    }

    private fun setMoviesResponse(
        mainViewModel: MainViewModel,
        type: String,
        it: ListMoviesEntry
    ) {
        when (type) {
            StringUtils.POPULAR -> {
                mainViewModel.setPopularMovieResponse(it)
            }

            StringUtils.UPCOMING -> {
                mainViewModel.setUpcomingMovieResponse(it)
            }

            StringUtils.NOWPLAYING -> {
                mainViewModel.setNowPlayingMovieResponse(it)
            }

            StringUtils.TOPRATED -> {
                mainViewModel.setTopRateMovieResponse(it)
            }
        }
    }
}
