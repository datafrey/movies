package com.datafrey.movies.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.datafrey.movies.R
import com.datafrey.movies.data.AllMovieInfo
import com.datafrey.movies.databinding.ActivityMovieInfoBinding
import com.datafrey.movies.toast
import com.datafrey.movies.viewmodels.MovieInfoViewModel

class MovieInfoActivity : AppCompatActivity(R.layout.activity_movie_info) {

    private lateinit var viewModel: MovieInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.title = "Loading info..."

        viewModel = ViewModelProviders.of(this)
            .get(MovieInfoViewModel::class.java)

        viewModel.getOccurredException()
            .observe(this, Observer { toast(it.message!!) })

        viewModel.getReceivedMovieInfo()
            .observe(this, Observer { fillActivityFieldsWithMovieInfo(it) })

        val imdbID = intent.getStringExtra("imdbID")
        viewModel.getMovieInfo(imdbID!!)
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