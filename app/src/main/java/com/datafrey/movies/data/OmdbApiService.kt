package com.datafrey.movies.data

import com.datafrey.movies.util.Util
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

    @GET(Util.API_KEY_QUERY)
    suspend fun getMoviesByQueue(@Query("s") queue: String): ShortMovieInfoSearch

    @GET(Util.API_KEY_QUERY)
    suspend fun getMovieByImdbID(@Query("i") imdbId: String): AllMovieInfo
}

object OmdbApi {
    val retrofitService: OmdbApiService by lazy {
        retrofit.create(OmdbApiService::class.java)
    }
}
