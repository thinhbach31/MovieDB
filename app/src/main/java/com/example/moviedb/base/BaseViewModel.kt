package com.example.moviedb.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    val eventLoading = MutableLiveData<Boolean>()
    val eventError = MutableLiveData<Event<BaseResponse>>()
    val eventFailure = MutableLiveData<Throwable>()

    fun showLoading(value: Boolean) {
        eventLoading.value = value
    }

    fun showError(baseResponse: BaseResponse) {
        eventError.value = Event(baseResponse)
    }

    fun showFailure(throwable: Throwable) {
        eventFailure.value = throwable
    }
}