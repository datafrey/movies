package com.datafrey.movies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.datafrey.movies.R
import com.datafrey.movies.activities.MovieInfoActivity
import com.datafrey.movies.data.ShortMovieInfo
import com.datafrey.movies.databinding.MovieItemBinding
import com.datafrey.movies.startActivity

class FoundMoviesViewAdapter(
    private var foundMoviesList: List<ShortMovieInfo>
) : RecyclerView.Adapter<FoundMoviesViewAdapter.FoundMovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FoundMovieViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_item, parent, false))

    override fun getItemCount() = foundMoviesList.size

    override fun onBindViewHolder(holder: FoundMovieViewHolder, position: Int) {
        val currentMovie = foundMoviesList[position]

        holder.binding.shortMoviewInfo = currentMovie
        holder.itemView.setOnClickListener {
            it.context.startActivity<MovieInfoActivity> {
                putExtra("imdbID", currentMovie.imdbID)
            }
        }
    }

    class FoundMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = MovieItemBinding.bind(itemView)
    }

}