package com.datafrey.movies.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.datafrey.movies.R
import com.datafrey.movies.data
import com.datafrey.movies.toast
import com.datafrey.movies.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_search.view.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this)
            .get(MainViewModel::class.java)

        viewModel.getOccurredException()
            .observe(this, Observer {
                if (it != null) {
                    toast(it.message!!)
                    viewModel.getOccurredException().value = null
                }
            })

        viewModel.getFoundMoviesListSize()
            .observe(this, Observer {
                foundMoviesListHintTextView.visibility =
                    if (it == 0) View.VISIBLE else View.GONE
            })

        foundMoviesRecyclerView.run {
            adapter = viewModel.getFoundMoviesAdapter()
            layoutManager = GridLayoutManager(
                baseContext,
                resources.getInteger(R.integer.column_count)
            )
        }
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