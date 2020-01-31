package com.example.moviedb.viewmodel.repository

import com.example.moviedb.api.APIBuilder
import com.example.moviedb.model.body.MoviesBody
import com.example.moviedb.viewmodel.viewmodel.MainViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

object MainVMRepository {

    fun getListMovies(
        disposable: CompositeDisposable, moviesBody: MoviesBody,
        mainViewModel: MainViewModel
    ) {
        disposable.add(
            APIBuilder.getWebService().getPopularMovies(
                moviesBody.api_key,
                moviesBody.language,
                moviesBody.page
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
                    mainViewModel.setListMovieResponse(it)
                }, {
                    mainViewModel.showFailure(it)
                })

        )
    }
}