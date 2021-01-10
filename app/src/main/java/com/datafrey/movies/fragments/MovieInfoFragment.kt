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

    private lateinit var imdbId: String

    private lateinit var binding: FragmentMovieInfoBinding

    private val viewModel: MovieInfoViewModel by lazy {
        ViewModelProvider(this, MovieInfoViewModelFactory(imdbId))
            .get(MovieInfoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        imdbId = MovieInfoFragmentArgs.fromBundle(requireArguments()).imdbId

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_movie_info, container, false)

        binding.let {
            it.viewModel = viewModel
            it.lifecycleOwner = this
        }

        viewModel.receivedMovieInfo.observe(viewLifecycleOwner, Observer {
            binding.loadingCurtainView.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
        })

        viewModel.occurredException.observe(viewLifecycleOwner, Observer {
            it?.let {
                toast(it.message!!)
                binding.progressBar.visibility = View.GONE
                requireActivity().actionBar!!.title = it.message
                viewModel.uiReactedToOccuredException()
            }
        })

        return binding.root
    }
}