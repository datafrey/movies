package com.datafrey.movies.data

import com.squareup.moshi.Json

data class ShortMovieInfoSearch(
    val Response: String,
    @Json(name = "Search") val searchResults: List<ShortMovieInfo>?,
    val totalResults: String
)