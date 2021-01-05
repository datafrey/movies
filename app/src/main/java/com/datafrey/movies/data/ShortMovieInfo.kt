package com.datafrey.movies.data

import com.squareup.moshi.Json

data class ShortMovieInfo(
    @Json(name = "Poster") val posterUrl: String,
    @Json(name = "Title") val title: String,
    @Json(name = "Type") val type: String,
    @Json(name = "Year") val year: String,
    @Json(name = "imdbID") val imdbId: String
)