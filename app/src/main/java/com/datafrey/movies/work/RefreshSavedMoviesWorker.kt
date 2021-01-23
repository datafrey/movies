package com.datafrey.movies.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.datafrey.movies.database.getSavedMoviesDatabase
import com.datafrey.movies.repository.MoviesRepository

class RefreshSavedMoviesWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "com.datafrey.movies.work.RefreshSavedMoviesWorker"
    }

    override suspend fun doWork(): Result {
        val repository = MoviesRepository(getSavedMoviesDatabase(applicationContext))
        try {
            repository.refreshSavedMovies()
            Log.i("SavedMoviesRefresh", "refreshed")
        } catch (exception: Exception) {
            exception.printStackTrace()
            return Result.retry()
        }

        return Result.success()
    }
}