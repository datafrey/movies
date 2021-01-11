package com.datafrey.movies.viewmodelfactories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.datafrey.movies.viewmodels.SavedMoviesViewModel

class SavedMoviesViewModelFactory(private val app: Application) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedMoviesViewModel::class.java)) {
            return SavedMoviesViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}