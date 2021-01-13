package com.datafrey.movies.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.datafrey.movies.R
import com.datafrey.movies.domain.DomainShortMovieInfo
import com.datafrey.movies.util.loadImageFromUrl

@BindingAdapter("app:url")
fun loadImage(imageView: ImageView, url: String?) =
    url?.let { imageView.loadImageFromUrl(url) }

@BindingAdapter("app:listData")
fun bindListOfMoviesToRecyclerView(
    recyclerView: RecyclerView,
    data: List<DomainShortMovieInfo>?
) {
    val adapter = recyclerView.adapter as ShortMovieInfoRecyclerViewAdapter
    adapter.submitList(data)
}

@BindingAdapter("app:movieSavedStatus")
fun setMovieSavedStatusImage(imageView: ImageView, isSaved: Boolean?) {
    isSaved?.let {
        imageView.setImageResource(
            if (isSaved) R.drawable.ic_white_delete_24
            else R.drawable.ic_white_save_24
        )
    }
}