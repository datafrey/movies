package com.datafrey.movies.network

import com.datafrey.movies.util.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private const val BASE_URL = "http://www.omdbapi.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface OmdbApiService {

    @GET(Constants.API_KEY_QUERY)
    suspend fun getMoviesByQuery(@Query("s") query: String): MovieSearchCallback

    @GET(Constants.API_KEY_QUERY)
    suspend fun getMovieByImdbId(@Query("i") imdbId: String): NetworkAllMovieInfo
}

object OmdbApi {
    val service: OmdbApiService by lazy {
        retrofit.create(OmdbApiService::class.java)
    }
}
