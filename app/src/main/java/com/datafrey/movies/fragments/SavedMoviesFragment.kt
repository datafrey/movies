package com.datafrey.movies.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.datafrey.movies.R
import com.datafrey.movies.adapters.ShortMovieInfoRecyclerViewAdapter
import com.datafrey.movies.databinding.FragmentSavedMoviesBinding
import com.datafrey.movies.domain.DomainShortMovieInfo
import com.datafrey.movies.util.toast
import com.datafrey.movies.viewmodelfactories.SavedMoviesViewModelFactory
import com.datafrey.movies.viewmodels.SavedMoviesViewModel

class SavedMoviesFragment : Fragment() {

    private lateinit var binding: FragmentSavedMoviesBinding

    private val viewModel: SavedMoviesViewModel by lazy {
        ViewModelProvider(this, SavedMoviesViewModelFactory(requireActivity().application))
            .get(SavedMoviesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_saved_movies, container, false
        )

        binding.let {
            it.viewModel = viewModel
            it.lifecycleOwner = this
        }

        binding.savedMoviesRecyclerView.adapter = ShortMovieInfoRecyclerViewAdapter(
            ShortMovieInfoRecyclerViewAdapter.OnClickListener { movieInfo ->
                showAllMovieInfo(movieInfo)
            })

        binding.clearSavedMoviesButton.setOnClickListener { onClearSavedMoviesButtonClick() }

        return binding.root
    }

    private fun showAllMovieInfo(movieInfo: DomainShortMovieInfo) {
        this.findNavController().navigate(
            SavedMoviesFragmentDirections
                .actionSavedMoviesFragmentToMovieInfoFragment(movieInfo.imdbId)
        )
    }

    private fun onClearSavedMoviesButtonClick() {
        if (!viewModel.isSavedMoviesListEmpty.value!!) {
            AlertDialog.Builder(requireActivity())
                .setMessage("Are you sure you want to delete all saved movies?")
                .setPositiveButton("Yes") { dialog, _ ->
                    viewModel.clearSavedMovies()
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
                .show()
        } else {
            toast("You don't have any saved movies.")
        }
    }
}