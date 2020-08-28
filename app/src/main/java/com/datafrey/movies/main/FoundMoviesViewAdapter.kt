package com.datafrey.movies.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.datafrey.movies.R
import com.datafrey.movies.data.ShortMovieInfo
import com.datafrey.movies.databinding.MovieItemBinding

class FoundMoviesViewAdapter(
    private var foundMoviesList: List<ShortMovieInfo>
) : RecyclerView.Adapter<FoundMoviesViewAdapter.FoundMovieViewHolder>() {

    private var movieItemEventListener: MovieItemEventListener? = null

    fun setMovieItemEventListener(movieItemEventListener: MovieItemEventListener) {
        this.movieItemEventListener = movieItemEventListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FoundMovieViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false))

    override fun getItemCount() = foundMoviesList.size

    override fun onBindViewHolder(holder: FoundMovieViewHolder, position: Int) {
        val currentMovie = foundMoviesList[position]
        holder.binding.shortMovieInfo = currentMovie
        holder.binding.movieItemEventListener = movieItemEventListener
    }

    class FoundMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = MovieItemBinding.bind(itemView)
    }

}

interface MovieItemEventListener {
    fun onClick(clickedItemMovieInfo: ShortMovieInfo)
}