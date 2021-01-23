package com.datafrey.movies

import android.os.Build
import androidx.multidex.MultiDexApplication
import androidx.work.*
import com.datafrey.movies.work.RefreshSavedMoviesWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MoviesApplication : MultiDexApplication() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        initWorks()
    }

    private fun setupRefreshSavedMoviesWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }
            .build()

        val request = PeriodicWorkRequestBuilder<RefreshSavedMoviesWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshSavedMoviesWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    private fun initWorks() {
        applicationScope.launch {
            setupRefreshSavedMoviesWork()
        }
    }
}