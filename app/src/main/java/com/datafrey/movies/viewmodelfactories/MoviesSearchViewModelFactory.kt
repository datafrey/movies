package com.datafrey.movies.viewmodelfactories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.datafrey.movies.viewmodels.MoviesSearchViewModel

class MoviesSearchViewModelFactory(private val app: Application) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesSearchViewModel::class.java)) {
            return MoviesSearchViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}