package com.datafrey.movies.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.datafrey.movies.R
import com.datafrey.movies.loadPoster
import com.datafrey.movies.omdb.AllMovieInfo
import com.datafrey.movies.omdb.OmdbService
import com.datafrey.movies.toast
import kotlinx.android.synthetic.main.activity_movie_info.*
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

    @SuppressLint("SetTextI18n")
    private fun fillingActivityFieldsWithMovieInfo(allMovieInfo: AllMovieInfo?) {
        if (allMovieInfo != null) {
            with (allMovieInfo) {
                with (supportActionBar!!) {
                    title = "$Title ($Year)"
                    subtitle = "IMDB id: $imdbID"
                    setDisplayHomeAsUpEnabled(true)
                }

                loadPoster(Poster, posterImageView)
                titleTextView.text = "Title: $Title"
                yearTextView.text = "Year: $Year"
                ratedTextView.text = "Rated: $Rated"
                releasedTextView.text = "Released: $Released"
                runtimeTextView.text = "Runtime: $Runtime"
                genreTextView.text = "Genre: $Genre"
                directorTextView.text = "Director: $Director"
                writerTextView.text = "Writer: $Writer"
                actorsTextView.text = "Actors: $Actors"
                plotTextView.text = "Plot: $Plot"
                languageTextView.text = "Language: $Language"
                countryTextView.text = "Country: $Country"
                awardsTextView.text = "Awards: $Awards"
            }
        } else
            toast("Loading failed.")
    }
}