package com.datafrey.movies.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.datafrey.movies.viewmodels.MovieInfoViewModel

class MovieInfoViewModelFactory(
    private val imdbID: String
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieInfoViewModel::class.java)) {
            return MovieInfoViewModel(imdbID) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}