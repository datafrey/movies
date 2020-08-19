package com.datafrey.movies.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OmdbService {

    private const val BASE_URL = "http://www.omdbapi.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(OmdbApi::class.java)

    fun getApi() = api

}