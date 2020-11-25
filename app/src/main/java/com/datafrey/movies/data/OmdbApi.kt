package com.datafrey.movies.data

import com.datafrey.movies.util.Util
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {

    @GET(Util.API_KEY_QUERY)
    fun getMoviesByQueue(@Query("s") queue: String): Call<ShortMovieInfoSearch>

    @GET(Util.API_KEY_QUERY)
    fun getMovieByImdbID(@Query("i") imdbID: String): Call<AllMovieInfo>
}