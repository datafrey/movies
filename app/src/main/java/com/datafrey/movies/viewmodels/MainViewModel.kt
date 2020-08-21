package com.datafrey.movies.viewmodels

import android.accounts.NetworkErrorException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.datafrey.movies.adapters.FoundMoviesViewAdapter
import com.datafrey.movies.data.OmdbService
import com.datafrey.movies.data.ShortMovieInfo
import com.datafrey.movies.data.ShortMovieInfoSearch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val foundMoviesList = mutableListOf<ShortMovieInfo>()
    private val foundMoviesListSize =
        MutableLiveData<Int>(foundMoviesList.size)
    private val foundMoviesAdapter =
        FoundMoviesViewAdapter(foundMoviesList)

    private val occurredException = MutableLiveData<Exception?>()

    fun getFoundMoviesListSize() = foundMoviesListSize

    fun getFoundMoviesAdapter() = foundMoviesAdapter

    fun getOccurredException() = occurredException

    fun searchMovies(query: String) {
        if (query.isNotEmpty()) {
            OmdbService.getApi()
                .getMoviesByQueue(query)
                .enqueue(object : Callback<ShortMovieInfoSearch> {
                    override fun onFailure(call: Call<ShortMovieInfoSearch>, t: Throwable) {
                        occurredException.value = NetworkErrorException("Loading failed, " +
                                "please check your network connection.")
                    }

                    override fun onResponse(
                        call: Call<ShortMovieInfoSearch>,
                        response: Response<ShortMovieInfoSearch>
                    ) {
                        foundMoviesList.clear()
                        val searchResults = response.body()?.searchResults
                        if (searchResults == null) {
                            foundMoviesAdapter.notifyDataSetChanged()
                            occurredException.value =
                                NullPointerException("Nothing have been found.")
                        } else {
                            foundMoviesList.addAll(searchResults)
                            foundMoviesAdapter.notifyDataSetChanged()
                        }
                        foundMoviesListSize.value = foundMoviesList.size
                    }
                })
        } else {
            occurredException.value =
                IllegalArgumentException("Please input a keyword/keyphrase.")
        }
    }

}