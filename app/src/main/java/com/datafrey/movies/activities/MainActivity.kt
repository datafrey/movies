package com.datafrey.movies.activities

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.datafrey.movies.R
import com.datafrey.movies.adapters.ShortMovieInfoRecyclerViewAdapter
// import com.datafrey.movies.adapters.MovieItemEventListener
import com.datafrey.movies.databinding.ActivityMainBinding
import com.datafrey.movies.util.data
import com.datafrey.movies.util.startActivity
import com.datafrey.movies.util.toast
import com.datafrey.movies.viewmodelfactories.MainViewModelFactory
import com.datafrey.movies.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_search.view.*

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, MainViewModelFactory())
            .get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.let {
            setContentView(it.root)
            it.viewModel = viewModel
            it.lifecycleOwner = this
        }

        foundMoviesRecyclerView.adapter = ShortMovieInfoRecyclerViewAdapter(
            ShortMovieInfoRecyclerViewAdapter.OnClickListener { movieInfo ->
                startActivity<MovieInfoActivity> {
                    putExtra("imdbID", movieInfo.imdbId)
                }
            })

        searchFloatingActionButton.setOnClickListener { onSearchButtonClick() }

        foundMoviesRecyclerView.layoutManager = GridLayoutManager(
            baseContext,
            resources.getInteger(R.integer.column_count)
        )

        viewModel.occurredException.observe(this, Observer {
            it?.let {
                toast(it.message!!)
                viewModel.cleanFoundMoviesList()
                viewModel.uiReactedToOccuredException()
            }
        })
    }

    private fun onSearchButtonClick() {
        val searchView = LayoutInflater.from(this)
            .inflate(R.layout.layout_search, null)

        AlertDialog.Builder(this)
            .setView(searchView)
            .setPositiveButton("Search") { dialog, _ ->
                foundMoviesRecyclerView.smoothScrollToPosition(0)
                viewModel.searchMovies(searchView.queryEditText.data)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}