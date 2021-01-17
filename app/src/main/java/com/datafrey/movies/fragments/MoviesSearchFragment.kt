package com.datafrey.movies.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.datafrey.movies.R
import com.datafrey.movies.adapters.ShortMovieInfoRecyclerViewAdapter
import com.datafrey.movies.databinding.FragmentMoviesSearchBinding
import com.datafrey.movies.domain.DomainShortMovieInfo
import com.datafrey.movies.util.data
import com.datafrey.movies.util.toast
import com.datafrey.movies.viewmodelfactories.MoviesSearchViewModelFactory
import com.datafrey.movies.viewmodels.MoviesSearchViewModel
import kotlinx.android.synthetic.main.layout_search.view.*

class MoviesSearchFragment : Fragment() {

    private lateinit var binding: FragmentMoviesSearchBinding

    private val viewModel: MoviesSearchViewModel by lazy {
        ViewModelProvider(this, MoviesSearchViewModelFactory(requireActivity().application))
            .get(MoviesSearchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movies_search, container, false
        )

        binding.let {
            it.viewModel = viewModel
            it.lifecycleOwner = this
        }

        binding.foundMoviesRecyclerView.adapter = ShortMovieInfoRecyclerViewAdapter(
            ShortMovieInfoRecyclerViewAdapter.OnClickListener { movieInfo ->
                showAllMovieInfo(movieInfo)
            })

        binding.searchFloatingActionButton.setOnClickListener { onSearchButtonClick() }
        binding.savedMoviesFloatingActionButton.setOnClickListener { onSavedMoviesButtonClick() }

        viewModel.occurredInputValidationException.observe(viewLifecycleOwner, Observer {
            it?.let {
                toast(it.message!!)
                viewModel.uiReactedToOccurredInputValidationException()
            }
        })

        viewModel.occurredRepositoryException.observe(viewLifecycleOwner, Observer {
            it?.let {
                toast(it.message!!)
                viewModel.uiReactedToOccurredRepositoryException()
            }
        })

        requireActivity().onBackPressedDispatcher.addCallback(this) { onBackPressed() }

        return binding.root
    }

    private fun showAllMovieInfo(movieInfo: DomainShortMovieInfo) {
        this.findNavController().navigate(MoviesSearchFragmentDirections
                .actionMoviesSearchFragmentToMovieInfoFragment(movieInfo.imdbId))
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
                dialog.cancel()
            }
            .create()
            .show()
    }

    private fun onSavedMoviesButtonClick() {
        binding.root.findNavController().navigate(
            R.id.action_moviesSearchFragment_to_savedMoviesFragment
        )
    }

    private fun onBackPressed() {
        AlertDialog.Builder(requireActivity())
            .setMessage("Are you sure you want to close the application?")
            .setPositiveButton("Yes") { dialog, _ ->
                requireActivity().finish()
            }
            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}