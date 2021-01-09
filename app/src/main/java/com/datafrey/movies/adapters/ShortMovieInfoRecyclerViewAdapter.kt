package com.datafrey.movies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.datafrey.movies.databinding.MovieItemBinding
import com.datafrey.movies.domain.DomainShortMovieInfo

class ShortMovieInfoRecyclerViewAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<DomainShortMovieInfo,
                ShortMovieInfoRecyclerViewAdapter.MovieInfoViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieInfoViewHolder(MovieItemBinding.inflate(
            LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: MovieInfoViewHolder, position: Int) {
        val currentMovie = getItem(position)
        holder.bind(currentMovie, onClickListener)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DomainShortMovieInfo>() {
        override fun areItemsTheSame(oldItem: DomainShortMovieInfo, newItem: DomainShortMovieInfo) =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: DomainShortMovieInfo, newItem: DomainShortMovieInfo) =
            oldItem.imdbId == newItem.imdbId
    }

    class MovieInfoViewHolder(
        private val binding: MovieItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movieInfo: DomainShortMovieInfo, onClickListener: OnClickListener) {
            binding.let {
                it.shortMovieInfo = movieInfo
                it.onClickListener = onClickListener
                it.executePendingBindings()
            }
        }
    }

    class OnClickListener(val clickListener: (clickedItemMovieInfo: DomainShortMovieInfo) -> Unit) {
        fun onClick(clickedItemMovieInfo: DomainShortMovieInfo) =
            clickListener(clickedItemMovieInfo)
    }
}
