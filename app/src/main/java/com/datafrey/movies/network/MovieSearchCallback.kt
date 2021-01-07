package com.datafrey.movies.network

import com.squareup.moshi.Json

data class MovieSearchCallback(
    @Json(name = "Response") val response: String,
    @Json(name = "Search") val searchResult: List<NetworkShortMovieInfo>?,
    val totalResults: String
)