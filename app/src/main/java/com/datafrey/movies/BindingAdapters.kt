package com.datafrey.movies

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:url")
fun loadImage(view: ImageView, url: String?) = loadPoster(url!!, view)