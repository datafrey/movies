package com.datafrey.movies.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.datafrey.movies.data.AllMovieInfo
import com.datafrey.movies.databinding.ActivityMovieInfoBinding
import com.datafrey.movies.util.toast
import com.datafrey.movies.viewmodelfactories.MovieInfoViewModelFactory
import com.datafrey.movies.viewmodels.MovieInfoViewModel
import kotlinx.android.synthetic.main.activity_movie_info.*

class MovieInfoActivity : AppCompatActivity() {

    private lateinit var imdbId: String

    private val binding: ActivityMovieInfoBinding by lazy {
        ActivityMovieInfoBinding.inflate(layoutInflater)
    }

    private val viewModel: MovieInfoViewModel by lazy {
        ViewModelProvider(this, MovieInfoViewModelFactory(imdbId))
            .get(MovieInfoViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar!!.title = "Loading info..."

        imdbId = intent.getStringExtra("imdbID")!!

        viewModel.receivedMovieInfo.observe(this, Observer {
            fillActivityFieldsWithMovieInfo(it)
            loadingCurtainView.visibility = View.GONE
            progressBar.visibility = View.GONE
        })

        viewModel.occurredException.observe(this, Observer {
            it?.let {
                toast(it.message!!)
                progressBar.visibility = View.GONE
                supportActionBar!!.title = it.message
                viewModel.uiReactedToOccuredException()
            }
        })
    }

    private fun fillActivityFieldsWithMovieInfo(allMovieInfo: AllMovieInfo) {
        binding.allMovieInfo = allMovieInfo

        supportActionBar!!.run {
            title = "${allMovieInfo.title} (${allMovieInfo.year})"
            subtitle = "IMDB id: ${allMovieInfo.imdbId}"
            setDisplayHomeAsUpEnabled(true)
        }
    }
}