package com.datafrey.movies.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.datafrey.movies.network.NetworkAllMovieInfo
import com.datafrey.movies.network.OmdbApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class MovieInfoViewModel(private val imdbId: String) : ViewModel() {

    private val _receivedMovieInfo = MutableLiveData<NetworkAllMovieInfo>()
    val receivedMovieInfoNetwork: LiveData<NetworkAllMovieInfo>
        get() = _receivedMovieInfo

    private val _occurredException = MutableLiveData<Exception?>()
    val occurredException: LiveData<Exception?>
        get() = _occurredException

    fun uiReactedToOccuredException() {
        _occurredException.value = null
    }

    init {
        getMovieInfo()
    }

    fun getMovieInfo() {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                _receivedMovieInfo.postValue(OmdbApi.retrofitService.getMovieByImdbID(imdbId))
            } catch (uhe: UnknownHostException) {
                _occurredException.postValue(UnknownHostException("Connection error."))
            } catch (e: Exception) {
                _occurredException.postValue(Exception("An error occured."))
            }
        }
    }
}