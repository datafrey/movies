package com.datafrey.movies.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.datafrey.movies.domain.DomainShortMovieInfo
import com.datafrey.movies.util.loadImageFromUrl

@BindingAdapter("app:url")
fun loadImage(imageView: ImageView, url: String?) =
    url?.let { imageView.loadImageFromUrl(url) }

@BindingAdapter("app:listData")
fun bindListOfMoviesToRecyclerView(recyclerView: RecyclerView,
                                   data: List<DomainShortMovieInfo>?) {
    val adapter = recyclerView.adapter as ShortMovieInfoRecyclerViewAdapter
    adapter.submitList(data)
}