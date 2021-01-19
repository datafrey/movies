package com.datafrey.movies.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.datafrey.movies.database.getSavedMoviesDatabase
import com.datafrey.movies.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SavedMoviesViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = MoviesRepository(getSavedMoviesDatabase(getApplication()))

    val savedMoviesList = repository.getAllSavedMoviesShortMovieInfos()
    val isSavedMoviesListEmpty = Transformations.map(savedMoviesList) { it.isEmpty() }

    fun clearSavedMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearSavedMovies()
        }
    }
}