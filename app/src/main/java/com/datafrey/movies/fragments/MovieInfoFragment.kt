package com.datafrey.movies.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.datafrey.movies.R
import com.datafrey.movies.databinding.FragmentMovieInfoBinding
import com.datafrey.movies.util.toast
import com.datafrey.movies.viewmodelfactories.MovieInfoViewModelFactory
import com.datafrey.movies.viewmodels.MovieInfoViewModel

class MovieInfoFragment : Fragment() {

    private lateinit var binding: FragmentMovieInfoBinding
    private lateinit var viewModel: MovieInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val imdbId = MovieInfoFragmentArgs.fromBundle(requireArguments()).imdbId

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_movie_info, container, false)

        viewModel = ViewModelProvider(
            this,
            MovieInfoViewModelFactory(requireActivity().application, imdbId)
        ).get(MovieInfoViewModel::class.java)

        binding.let {
            it.viewModel = viewModel
            it.lifecycleOwner = this
        }

        binding.saveDeleteButton.setOnClickListener { viewModel.changeMovieSavedStatus() }

        viewModel.occurredRepositoryException.observe(viewLifecycleOwner, Observer {
            it?.let {
                toast(it.message!!)
                binding.progressBar.visibility = View.GONE
                binding.errorMessageTextView.run {
                    text = it.message
                    visibility = View.VISIBLE
                }
                viewModel.uiReactedToOccurredRepositoryException()
            }
        })

        return binding.root
    }
}