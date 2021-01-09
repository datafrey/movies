package com.datafrey.movies.viewmodels

import androidx.lifecycle.*
import com.datafrey.movies.adapters.toListOfDomainShortMovieInfos
import com.datafrey.movies.domain.DomainShortMovieInfo
import com.datafrey.movies.network.OmdbApi
import com.squareup.moshi.JsonDataException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class MainViewModel : ViewModel() {

    private val _foundMoviesList = MutableLiveData<List<DomainShortMovieInfo>>(listOf())
    val foundMoviesList: LiveData<List<DomainShortMovieInfo>>
        get() = _foundMoviesList

    val foundMoviesListIsEmpty: LiveData<Boolean>
        get() = Transformations.map(_foundMoviesList) { it.isEmpty() }

    private val _occurredException = MutableLiveData<Exception?>()
    val occurredException: LiveData<Exception?>
        get() = _occurredException

    fun uiReactedToOccuredException() {
        _occurredException.value = null
    }

    fun cleanFoundMoviesList() {
        _foundMoviesList.value = listOf()
    }

    fun searchMovies(query: String) {
        if (query.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.Default) {
                try {
                    val searchCallback = OmdbApi.service.getMoviesByQuery(query)
                    _foundMoviesList.postValue(searchCallback.searchResult!!.toListOfDomainShortMovieInfos())
                } catch (uhe: UnknownHostException) {
                    _occurredException.postValue(UnknownHostException("Connection error."))
                } catch (jde: JsonDataException) {
                    _occurredException.postValue(JsonDataException("Nothing have been found."))
                } catch (e: Exception) {
                    _occurredException.postValue(Exception("An error occured."))
                }
            }
        } else {
            _occurredException.value = IllegalArgumentException("Please input a keyword/keyphrase.")
        }
    }
}