package com.datafrey.movies.data

import com.squareup.moshi.Json

data class MovieSearchCallback(
    @Json(name = "Response") val response: String,
    @Json(name = "Search") val searchResult: List<ShortMovieInfo>?,
    val totalResults: String
)