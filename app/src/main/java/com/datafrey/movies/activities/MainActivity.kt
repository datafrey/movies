package com.datafrey.movies.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.datafrey.movies.R
import com.datafrey.movies.adapters.FoundMoviesViewAdapter
import com.datafrey.movies.adapters.MovieItemEventListener
import com.datafrey.movies.data.ShortMovieInfo
import com.datafrey.movies.util.data
import com.datafrey.movies.util.startActivity
import com.datafrey.movies.util.toast
import com.datafrey.movies.viewmodelfactories.MainViewModelFactory
import com.datafrey.movies.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_search.view.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, MainViewModelFactory())
            .get(MainViewModel::class.java)

        viewModel.occurredException.observe(this, Observer {
            it?.let {
                toast(it.message!!)
                viewModel.cleanFoundMoviesList()
                viewModel.uiReactedToOccuredException()
            }
        })

        viewModel.foundMoviesListIsEmpty.observe(this, Observer {
            foundMoviesListHintTextView.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.foundMoviesList.observe(this, Observer {
            it?.let {
                val moviesAdapter = FoundMoviesViewAdapter(it)
                moviesAdapter.setMovieItemEventListener(object : MovieItemEventListener {
                    override fun onClick(clickedItemMovieInfo: ShortMovieInfo) {
                        startActivity<MovieInfoActivity> {
                            putExtra("imdbID", clickedItemMovieInfo.imdbID)
                        }
                    }
                })
                foundMoviesRecyclerView.adapter = moviesAdapter
                moviesAdapter.notifyDataSetChanged()
            }
        })

        foundMoviesRecyclerView.layoutManager = GridLayoutManager(
            baseContext,
            resources.getInteger(R.integer.column_count)
        )
    }

    fun searchButtonClick(view: View) {
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