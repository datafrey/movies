package com.datafrey.movies.domain

data class DomainShortMovieInfo(
    val imdbId: String,
    val posterUrl: String,
    val title: String,
    val type: String,
    val year: String
)
