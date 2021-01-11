package com.datafrey.movies.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.datafrey.movies.database.getSavedMoviesDatabase
import com.datafrey.movies.domain.DomainAllMovieInfo
import com.datafrey.movies.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieInfoViewModel(
    app: Application,
    private val imdbId: String
) : AndroidViewModel(app) {

    private val repository = MoviesRepository(getSavedMoviesDatabase(getApplication()))

    val receivedMovieInfo: LiveData<DomainAllMovieInfo>
        get() = repository.requestedMovieInfo

    val isMovieInfoReceived: LiveData<Boolean>
        get() = Transformations.map(receivedMovieInfo) { it != null }

    val occurredRepositoryException: LiveData<Exception?>
        get() = repository.occurredException

    fun uiReactedToOccurredRepositoryException() {
        repository.occurredExceptionHandled()
    }

    init {
        getMovieInfo()
    }

    fun getMovieInfo() {
        viewModelScope.launch(Dispatchers.Default) {
            repository.getMovieInfo(imdbId)
        }
    }
}