package com.datafrey.movies.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.datafrey.movies.database.getSavedMoviesDatabase
import com.datafrey.movies.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieInfoViewModel(app: Application, imdbId: String) : AndroidViewModel(app) {

    private val repository = MoviesRepository(getSavedMoviesDatabase(getApplication()))

    val receivedMovieInfo = repository.requestedMovieInfo
    val isMovieInfoReceived = Transformations.map(receivedMovieInfo) { it != null }
    val isMovieSavedInDatabase = repository.getIsMovieSavedInDatabase(imdbId)

    val occurredRepositoryException = repository.occurredException

    fun uiReactedToOccurredRepositoryException() {
        repository.occurredExceptionHandled()
    }

    init {
        loadMovieInfo(imdbId)
    }

    private fun loadMovieInfo(imdbId: String) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.getMovieInfo(imdbId)
        }
    }

    fun changeMovieSavedStatus() {
        if (isMovieSavedInDatabase.value!!) {
            deleteMovieFromDatabase()
        } else {
            saveMovieInDatabase()
        }
    }

    private fun saveMovieInDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveMovieInDatabase(receivedMovieInfo.value!!)
        }
    }

    private fun deleteMovieFromDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMovieFromDatabase(receivedMovieInfo.value!!.imdbId)
        }
    }
}