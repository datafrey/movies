package com.datafrey.movies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.datafrey.movies.data.ShortMovieInfo
import com.datafrey.movies.databinding.MovieItemBinding

class FoundMoviesViewAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<ShortMovieInfo,
                FoundMoviesViewAdapter.FoundMovieViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FoundMovieViewHolder(MovieItemBinding.inflate(
            LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: FoundMovieViewHolder, position: Int) {
        val currentMovie = getItem(position)
        holder.bind(currentMovie, onClickListener)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ShortMovieInfo>() {
        override fun areItemsTheSame(oldItem: ShortMovieInfo, newItem: ShortMovieInfo) =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: ShortMovieInfo, newItem: ShortMovieInfo) =
            oldItem.imdbId == newItem.imdbId
    }

    class FoundMovieViewHolder(
        private val binding: MovieItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(shortMovieInfo: ShortMovieInfo, onClickListener: OnClickListener) {
            binding.let {
                it.shortMovieInfo = shortMovieInfo
                it.onClickListener = onClickListener
                it.executePendingBindings()
            }
        }
    }

    class OnClickListener(val clickListener: (clickedItemMovieInfo: ShortMovieInfo) -> Unit) {
        fun onClick(clickedItemMovieInfo: ShortMovieInfo) = clickListener(clickedItemMovieInfo)
    }
}
