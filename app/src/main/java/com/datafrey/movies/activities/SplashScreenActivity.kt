package com.datafrey.movies.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.datafrey.movies.database.getSavedMoviesDatabase
import com.datafrey.movies.network.OmdbApi
import com.datafrey.movies.util.startActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        OmdbApi.service
        getSavedMoviesDatabase(application)

        startActivity<MainActivity>()
        finish()
    }
}