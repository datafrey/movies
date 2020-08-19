package com.datafrey.movies.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.datafrey.movies.R
import com.datafrey.movies.data.AllMovieInfo
import com.datafrey.movies.data.OmdbService
import com.datafrey.movies.databinding.ActivityMovieInfoBinding
import com.datafrey.movies.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieInfoActivity : AppCompatActivity(R.layout.activity_movie_info) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gettingMovieInfo()
    }

    private fun gettingMovieInfo() {
        val imdbID = intent.getStringExtra("imdbID")
        OmdbService.getApi()
            .getMovieByImdbID(imdbID!!)
            .enqueue(object : Callback<AllMovieInfo> {
                override fun onFailure(call: Call<AllMovieInfo>, t: Throwable) =
                    toast("Loading failed: ${t.message}")

                override fun onResponse(
                    call: Call<AllMovieInfo>,
                    response: Response<AllMovieInfo>
                ) {
                    try {
                        fillingActivityFieldsWithMovieInfo(response.body()!!)
                    } catch (npe: NullPointerException) {
                        toast("Loading failed.")
                    }
                }
            })
    }

    private fun fillingActivityFieldsWithMovieInfo(allMovieInfo: AllMovieInfo?) {
        val binding: ActivityMovieInfoBinding =
            setContentView(this, R.layout.activity_movie_info)
        binding.allMovieInfo = allMovieInfo

        allMovieInfo!!.run {
            supportActionBar!!.run {
                title = "$Title ($Year)"
                subtitle = "IMDB id: $imdbID"
                setDisplayHomeAsUpEnabled(true)
            }
        }
    }

}