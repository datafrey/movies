package com.datafrey.movies.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.datafrey.movies.data.AllMovieInfo
import com.datafrey.movies.data.OmdbApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class MovieInfoViewModel(
    private val imdbID: String
) : ViewModel() {

    private val _receivedMovieInfo = MutableLiveData<AllMovieInfo>()
    val receivedMovieInfo: LiveData<AllMovieInfo>
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
                _receivedMovieInfo.postValue(OmdbApi.retrofitService.getMovieByImdbID(imdbID))
            } catch (uhe: UnknownHostException) {
                _occurredException.postValue(UnknownHostException("Connection error."))
            } catch (e: Exception) {
                _occurredException.postValue(Exception("An error occured."))
            }
        }
    }
}