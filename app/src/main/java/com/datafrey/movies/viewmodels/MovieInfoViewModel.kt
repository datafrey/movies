package com.datafrey.movies.viewmodels

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.datafrey.movies.data.AllMovieInfo
import com.datafrey.movies.data.OmdbService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieInfoViewModel(
    private val imdbID: String
) : ViewModel() {

    private val _receivedMovieInfo = MutableLiveData<AllMovieInfo>()
    val receivedMovieInfo: LiveData<AllMovieInfo>
        get() = _receivedMovieInfo

    private val _occurredException = MutableLiveData<Exception?>()
    val occurredException: LiveData<Exception?>
        get() = _occurredException

    init {
        getMovieInfo()
    }

    fun doneShowingExceptionMessage() {
        _occurredException.value = null
    }

    fun getMovieInfo() {
        OmdbService.getApi()
            .getMovieByImdbID(imdbID)
            .enqueue(object : Callback<AllMovieInfo> {
                override fun onFailure(call: Call<AllMovieInfo>, t: Throwable) {
                    _occurredException.value = NetworkErrorException("Loading failed, " +
                            "please check your network connection.")
                }

                override fun onResponse(
                    call: Call<AllMovieInfo>,
                    response: Response<AllMovieInfo>
                ) {
                    try {
                        _receivedMovieInfo.value = response.body()
                    } catch (npe: NullPointerException) {
                        _occurredException.value = NullPointerException("Loading failed.")
                    }
                }
            })
    }
}