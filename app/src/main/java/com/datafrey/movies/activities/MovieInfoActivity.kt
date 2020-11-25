package com.datafrey.movies.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.datafrey.movies.R
import com.datafrey.movies.data.AllMovieInfo
import com.datafrey.movies.databinding.ActivityMovieInfoBinding
import com.datafrey.movies.util.toast
import com.datafrey.movies.viewmodelfactories.MovieInfoViewModelFactory
import com.datafrey.movies.viewmodels.MovieInfoViewModel
import kotlinx.android.synthetic.main.activity_movie_info.*

class MovieInfoActivity : AppCompatActivity(R.layout.activity_movie_info) {

    private lateinit var viewModel: MovieInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.title = "Loading info..."

        val imdbID = intent.getStringExtra("imdbID")
        val viewModelFactory = MovieInfoViewModelFactory(imdbID!!)

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MovieInfoViewModel::class.java)

        viewModel.occurredException
            .observe(this, Observer {
                if (it != null) {
                    toast(it.message!!)
                    viewModel.doneShowingExceptionMessage()
                }
            })

        viewModel.receivedMovieInfo
            .observe(this, Observer {
                fillActivityFieldsWithMovieInfo(it)
                progressBar.visibility = View.GONE
            })
    }

    private fun fillActivityFieldsWithMovieInfo(allMovieInfo: AllMovieInfo?) {
        val binding: ActivityMovieInfoBinding =
            setContentView(this, R.layout.activity_movie_info)
        binding.allMovieInfo = allMovieInfo

        allMovieInfo!!.run {
            supportActionBar!!.run {
                title = "$Title ($Year)"
                subtitle = "IMDB id: $imdbID"
                setDisplayHomeAsUpEnabled(true)
            }
        }
    }
}