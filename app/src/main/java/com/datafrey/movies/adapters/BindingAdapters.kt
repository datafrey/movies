package com.datafrey.movies.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.datafrey.movies.network.NetworkShortMovieInfo
import com.datafrey.movies.util.loadImageFromUrl

@BindingAdapter("app:url")
fun loadImage(view: ImageView, url: String?) =
    url?.let { view.loadImageFromUrl(url) }

@BindingAdapter("app:listData")
fun bindListOfMoviesToRecyclerView(recyclerView: RecyclerView,
                                   data: List<NetworkShortMovieInfo>?) {
    val adapter = recyclerView.adapter as ShortMovieInfoRecyclerViewAdapter
    adapter.submitList(data)
}