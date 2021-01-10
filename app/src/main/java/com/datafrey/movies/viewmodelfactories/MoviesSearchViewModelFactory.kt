package com.datafrey.movies.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.datafrey.movies.viewmodels.MoviesSearchViewModel

class MoviesSearchViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesSearchViewModel::class.java)) {
            return MoviesSearchViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}