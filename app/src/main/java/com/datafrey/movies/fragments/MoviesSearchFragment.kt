package com.datafrey.movies.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.datafrey.movies.R
import com.datafrey.movies.adapters.ShortMovieInfoRecyclerViewAdapter
import com.datafrey.movies.databinding.FragmentMoviesSearchBinding
import com.datafrey.movies.util.data
import com.datafrey.movies.util.toast
import com.datafrey.movies.viewmodelfactories.MoviesSearchViewModelFactory
import com.datafrey.movies.viewmodels.MoviesSearchViewModel
import kotlinx.android.synthetic.main.layout_search.view.*

class MoviesSearchFragment : Fragment() {

    private lateinit var binding: FragmentMoviesSearchBinding

    private val viewModel: MoviesSearchViewModel by lazy {
        ViewModelProvider(this, MoviesSearchViewModelFactory())
            .get(MoviesSearchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_movies_search, container, false)

        binding.let {
            it.viewModel = viewModel
            it.lifecycleOwner = this
        }

        binding.foundMoviesRecyclerView.adapter = ShortMovieInfoRecyclerViewAdapter(
            ShortMovieInfoRecyclerViewAdapter.OnClickListener { movieInfo ->
                this.findNavController().navigate(MoviesSearchFragmentDirections
                    .actionMoviesSearchFragmentToMovieInfoFragment(movieInfo.imdbId))
            })

        binding.searchFloatingActionButton.setOnClickListener { onSearchButtonClick() }

        viewModel.occurredException.observe(viewLifecycleOwner, Observer {
            it?.let {
                toast(it.message!!)
                viewModel.clearFoundMoviesList()
                viewModel.uiReactedToOccuredException()
            }
        })

        return binding.root
    }

    private fun onSearchButtonClick() {
        val searchView = LayoutInflater.from(activity)
            .inflate(R.layout.layout_search, null)

        AlertDialog.Builder(requireActivity())
            .setView(searchView)
            .setPositiveButton("Search") { dialog, _ ->
                binding.foundMoviesRecyclerView.smoothScrollToPosition(0)
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