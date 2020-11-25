package com.datafrey.movies.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.datafrey.movies.util.loadImageFromUrl

@BindingAdapter("app:url")
fun loadImage(view: ImageView, url: String?) = view.loadImageFromUrl(url)