package com.datafrey.movies.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import com.datafrey.movies.database.getSavedMoviesDatabase
import com.datafrey.movies.repository.MoviesRepository

class SavedMoviesViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = MoviesRepository(getSavedMoviesDatabase(getApplication()))

    val savedMoviesList = repository.getAllSavedMoviesShortMovieInfos()
    val isSavedMoviesListEmpty = Transformations.map(savedMoviesList) { it.isEmpty() }
}