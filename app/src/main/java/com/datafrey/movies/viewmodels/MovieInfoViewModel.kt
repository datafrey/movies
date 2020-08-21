package com.datafrey.movies.viewmodels

import android.accounts.NetworkErrorException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.datafrey.movies.data.AllMovieInfo
import com.datafrey.movies.data.OmdbService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieInfoViewModel : ViewModel() {

    private val receivedMovieInfo = MutableLiveData<AllMovieInfo>()
    private val occurredException = MutableLiveData<Exception>()

    fun getReceivedMovieInfo() = receivedMovieInfo

    fun getOccurredException() = occurredException

    fun getMovieInfo(imdbID: String) {
        OmdbService.getApi()
            .getMovieByImdbID(imdbID)
            .enqueue(object : Callback<AllMovieInfo> {
                override fun onFailure(call: Call<AllMovieInfo>, t: Throwable) {
                    occurredException.value = NetworkErrorException("Loading failed, " +
                            "please check your network connection.")
                }

                override fun onResponse(
                    call: Call<AllMovieInfo>,
                    response: Response<AllMovieInfo>
                ) {
                    try {
                        receivedMovieInfo.value = response.body()
                    } catch (npe: NullPointerException) {
                        occurredException.value = NullPointerException("Loading failed.")
                    }
                }
            })
    }

}