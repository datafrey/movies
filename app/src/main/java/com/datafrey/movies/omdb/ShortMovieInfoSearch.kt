package com.datafrey.movies.omdb

import com.google.gson.annotations.SerializedName

data class ShortMovieInfoSearch(
    val Response: String,
    @SerializedName("Search")
    val searchResults: ArrayList<ShortMovieInfo>,
    val totalResults: String
)