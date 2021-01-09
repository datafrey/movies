package com.datafrey.movies.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.datafrey.movies.adapters.toDatabaseMovieInfo
import com.datafrey.movies.adapters.toDomainAllMovieInfo
import com.datafrey.movies.adapters.toListOfDomainShortMovieInfos
import com.datafrey.movies.database.SavedMoviesDatabase
import com.datafrey.movies.domain.DomainAllMovieInfo
import com.datafrey.movies.network.OmdbApi
import kotlinx.coroutines.Dispatchers
import java.net.UnknownHostException

class SavedMoviesRepository(database: SavedMoviesDatabase) {

    private val savedMoviesDao = database.savedMoviesDao

    val allSavedMoviesShortMovieInfos =
        Transformations.map(savedMoviesDao.getAllMovies()) {
            it.toListOfDomainShortMovieInfos()
        }

    private val _occurredException = MutableLiveData<Exception?>()
    val occurredException: LiveData<Exception?>
        get() = _occurredException

    suspend fun insertIntoDatabase(movie: DomainAllMovieInfo) {
        with(Dispatchers.IO) {
            savedMoviesDao.insert(movie.toDatabaseMovieInfo())
        }
    }

    suspend fun updateInDatabase(movie: DomainAllMovieInfo) {
        with(Dispatchers.IO) {
            savedMoviesDao.update(movie.toDatabaseMovieInfo())
        }
    }

    suspend fun getFromDatabase(imdbId: String): LiveData<DomainAllMovieInfo> {
        with(Dispatchers.IO) {
            return Transformations.map(savedMoviesDao.get(imdbId)) {
                it.toDomainAllMovieInfo()
            }
        }
    }

    suspend fun deleteFromDatabase(imdbId: String) {
        with(Dispatchers.IO) {
            savedMoviesDao.delete(imdbId)
        }
    }

    suspend fun clearMovies(imdbId: String) {
        with(Dispatchers.IO) {
            savedMoviesDao.clear()
        }
    }

    suspend fun refreshMovieInfo(imdbId: String) {
        with(Dispatchers.IO) {
            try {
                val newMovieInfo = OmdbApi.service.getMovieByImdbId(imdbId)
                savedMoviesDao.update(newMovieInfo.toDatabaseMovieInfo())
            } catch (uhe: UnknownHostException) {
                _occurredException.postValue(UnknownHostException("Connection error."))
            } catch (e: Exception) {
                _occurredException.postValue(Exception("An error occured."))
            }
        }
    }
}