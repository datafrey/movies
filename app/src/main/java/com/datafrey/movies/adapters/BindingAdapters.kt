package com.datafrey.movies.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.datafrey.movies.data.ShortMovieInfo
import com.datafrey.movies.util.loadImageFromUrl

@BindingAdapter("app:url")
fun loadImage(view: ImageView, url: String?) =
    url?.let { view.loadImageFromUrl(url) }

@BindingAdapter("app:listData")
fun bindListOfMoviesToRecyclerView(recyclerView: RecyclerView,
                                   data: List<ShortMovieInfo>?) {
    val adapter = recyclerView.adapter as FoundMoviesViewAdapter
    adapter.submitList(data)
}