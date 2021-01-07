package com.datafrey.movies.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class DatabaseMovieInfo(
    @PrimaryKey
    val imdbId: String,
    val actors: String,
    val awards: String,
    val country: String,
    val director: String,
    val genre: String,
    val language: String,
    val plot: String,
    val posterUrl: String,
    val rated: String,
    val released: String,
    val runtime: String,
    val title: String,
    val type: String,
    val writer: String,
    val year: String
)