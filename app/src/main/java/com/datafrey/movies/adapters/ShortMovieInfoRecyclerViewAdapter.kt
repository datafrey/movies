package com.datafrey.movies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.datafrey.movies.databinding.MovieItemBinding
import com.datafrey.movies.network.NetworkShortMovieInfo

class ShortMovieInfoRecyclerViewAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<NetworkShortMovieInfo,
                ShortMovieInfoRecyclerViewAdapter.FoundMovieViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FoundMovieViewHolder(MovieItemBinding.inflate(
            LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: FoundMovieViewHolder, position: Int) {
        val currentMovie = getItem(position)
        holder.bind(currentMovie, onClickListener)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<NetworkShortMovieInfo>() {
        override fun areItemsTheSame(oldItem: NetworkShortMovieInfo, newItem: NetworkShortMovieInfo) =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: NetworkShortMovieInfo, newItem: NetworkShortMovieInfo) =
            oldItem.imdbId == newItem.imdbId
    }

    class FoundMovieViewHolder(
        private val binding: MovieItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(networkShortMovieInfo: NetworkShortMovieInfo, onClickListener: OnClickListener) {
            binding.let {
                it.shortMovieInfo = networkShortMovieInfo
                it.onClickListener = onClickListener
                it.executePendingBindings()
            }
        }
    }

    class OnClickListener(val clickListener: (clickedItemMovieInfoNetwork: NetworkShortMovieInfo) -> Unit) {
        fun onClick(clickedItemMovieInfoNetwork: NetworkShortMovieInfo) = clickListener(clickedItemMovieInfoNetwork)
    }
}
