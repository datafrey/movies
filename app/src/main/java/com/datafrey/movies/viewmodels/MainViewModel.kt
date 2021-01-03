package com.datafrey.movies.viewmodels

import androidx.lifecycle.*
import com.datafrey.movies.data.OmdbApi
import com.datafrey.movies.data.ShortMovieInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _foundMoviesList = MutableLiveData<List<ShortMovieInfo>>(listOf())
    val foundMoviesList: LiveData<List<ShortMovieInfo>>
        get() = _foundMoviesList

    val foundMoviesListIsEmpty: LiveData<Boolean>
        get() = Transformations.map(_foundMoviesList) { it.isEmpty() }

    private val _occurredException = MutableLiveData<Exception?>()
    val occurredException: LiveData<Exception?>
        get() = _occurredException

    fun doneShowingExceptionMessage() {
        _occurredException.value = null
    }

    fun searchMovies(query: String) {
        if (query.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.Default) {
                try {
                    val searchResults = OmdbApi.retrofitService
                        .getMoviesByQueue(query)
                        .searchResults!!

                    _foundMoviesList.postValue(searchResults)
                } catch (e: Exception) {
                    _foundMoviesList.postValue(listOf())
                    _occurredException.postValue(NullPointerException("Nothing have been found."))
                }
            }
        } else {
            _occurredException.value = IllegalArgumentException("Please input a keyword/keyphrase.")
        }
    }
}