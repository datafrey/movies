package com.datafrey.movies.mainactivity

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
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
    private val foundMoviesAdapter =
        FoundMoviesViewAdapter(foundMoviesList)

    fun getFoundMoviesAdapter() = foundMoviesAdapter

    private val _foundMoviesListIsEmpty =
        MutableLiveData<Boolean>(foundMoviesList.isEmpty())
    val foundMoviesListIsEmpty: LiveData<Boolean>
        get() = _foundMoviesListIsEmpty

    private val _occurredException = MutableLiveData<Exception?>()
    val occurredException: LiveData<Exception?>
        get() = _occurredException

    fun doneShowingExceptionMessage() {
        _occurredException.value = null
    }

    fun searchMovies(query: String) {
        if (query.isNotEmpty()) {
            OmdbService.getApi()
                .getMoviesByQueue(query)
                .enqueue(object : Callback<ShortMovieInfoSearch> {
                    override fun onFailure(call: Call<ShortMovieInfoSearch>, t: Throwable) {
                        _occurredException.value = NetworkErrorException("Loading failed, " +
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
                            _occurredException.value =
                                NullPointerException("Nothing have been found.")
                        } else {
                            foundMoviesList.addAll(searchResults)
                            foundMoviesAdapter.notifyDataSetChanged()
                        }
                        _foundMoviesListIsEmpty.value = foundMoviesList.isEmpty()
                    }
                })
        } else {
            _occurredException.value =
                IllegalArgumentException("Please input a keyword/keyphrase.")
        }
    }

}