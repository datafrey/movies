package com.datafrey.movies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.datafrey.movies.R
import com.datafrey.movies.activities.MovieInfoActivity
import com.datafrey.movies.loadPoster
import com.datafrey.movies.omdb.ShortMovieInfo
import com.datafrey.movies.startActivity
import kotlinx.android.synthetic.main.movie_item.view.*

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

        with (holder) {
            titleTextView.text = currentMovie.Title
            yearTextView.text = currentMovie.Year
            loadPoster(currentMovie.Poster, posterImageView)

            itemView.setOnClickListener {
                it.context.startActivity<MovieInfoActivity> {
                    putExtra("imdbID", currentMovie.imdbID)
                }
            }
        }
    }

    class FoundMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val posterImageView = itemView.posterImageView
        val titleTextView = itemView.titleTextView
        val yearTextView = itemView.yearTextView
    }
}