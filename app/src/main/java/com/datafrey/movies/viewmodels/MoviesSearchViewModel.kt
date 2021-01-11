package com.datafrey.movies.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.datafrey.movies.database.getSavedMoviesDatabase
import com.datafrey.movies.domain.DomainShortMovieInfo
import com.datafrey.movies.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesSearchViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = MoviesRepository(getSavedMoviesDatabase(getApplication()))

    private val _foundMoviesList = MutableLiveData<List<DomainShortMovieInfo>>(listOf())
    val foundMoviesList: LiveData<List<DomainShortMovieInfo>>
        get() = _foundMoviesList

    val isFoundMoviesListEmpty: LiveData<Boolean>
        get() = Transformations.map(_foundMoviesList) { it.isEmpty() }

    private val _occurredInputValidationException = MutableLiveData<Exception?>()
    val occurredInputValidationException: LiveData<Exception?>
        get() = _occurredInputValidationException

    fun uiReactedToOccurredInputValidationException() {
        _occurredInputValidationException.value = null
    }

    val occurredRepositoryException: LiveData<Exception?>
        get() = repository.occurredException

    fun uiReactedToOccurredRepositoryException() {
        repository.occurredExceptionHandled()
    }

    fun searchMovies(query: String) {
        if (query.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.Default) {
                _foundMoviesList.postValue(repository.searchMoviesInNetwork(query))
            }
        } else {
            _occurredInputValidationException.value = IllegalArgumentException("Please input a keyword/keyphrase.")
        }
    }
}