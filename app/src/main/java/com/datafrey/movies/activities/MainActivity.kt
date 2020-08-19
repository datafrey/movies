package com.datafrey.movies.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.datafrey.movies.R
import com.datafrey.movies.adapters.FoundMoviesViewAdapter
import com.datafrey.movies.data
import com.datafrey.movies.data.OmdbService
import com.datafrey.movies.data.ShortMovieInfo
import com.datafrey.movies.data.ShortMovieInfoSearch
import com.datafrey.movies.toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private var foundMoviesList = mutableListOf<ShortMovieInfo>()
    private val foundMoviesAdapter =
        FoundMoviesViewAdapter(foundMoviesList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        foundMoviesRecyclerView.run {
            adapter = foundMoviesAdapter
            layoutManager = LinearLayoutManager(baseContext)
        }
    }

    fun searchButtonClick(view: View) {
        foundMoviesRecyclerView.smoothScrollToPosition(0)

        if (queryEditText.data.isNotEmpty()) {
            OmdbService.getApi()
                .getMoviesByQueue(queryEditText.data)
                .enqueue(object : Callback<ShortMovieInfoSearch> {
                    override fun onFailure(call: Call<ShortMovieInfoSearch>, t: Throwable) =
                        toast("Loading failed: ${t.message}")

                    override fun onResponse(
                        call: Call<ShortMovieInfoSearch>,
                        response: Response<ShortMovieInfoSearch>
                    ) {
                        foundMoviesList.clear()
                        try {
                            foundMoviesList.addAll(response.body()!!.searchResults)
                        } catch (npe: NullPointerException) {
                            toast("Nothing have been found.")
                        }
                        foundMoviesAdapter.notifyDataSetChanged()
                    }
                })
        } else {
            toast("Please input a keyword/keyphrase.")
        }
    }

}