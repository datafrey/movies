package com.datafrey.movies.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.datafrey.movies.adapters.toDatabaseMovieInfo
import com.datafrey.movies.adapters.toDomainAllMovieInfo
import com.datafrey.movies.adapters.toListOfDomainShortMovieInfos
import com.datafrey.movies.database.SavedMoviesDatabase
import com.datafrey.movies.domain.DomainAllMovieInfo
import com.datafrey.movies.domain.DomainShortMovieInfo
import com.datafrey.movies.network.OmdbApi
import com.squareup.moshi.JsonDataException
import java.net.UnknownHostException

class MoviesRepository(database: SavedMoviesDatabase) {

    private val savedMoviesDao = database.savedMoviesDao

    private val _requestedMovieInfo = MutableLiveData<DomainAllMovieInfo>(null)
    val requestedMovieInfo: LiveData<DomainAllMovieInfo>
        get() = _requestedMovieInfo

    private val _occurredException = MutableLiveData<Exception?>()
    val occurredException: LiveData<Exception?>
        get() = _occurredException

    fun occurredExceptionHandled() {
        _occurredException.value = null
    }

    suspend fun saveMovieInDatabase(movie: DomainAllMovieInfo) {
        savedMoviesDao.insert(movie.toDatabaseMovieInfo())
    }

    suspend fun deleteMovieFromDatabase(imdbId: String) {
        savedMoviesDao.delete(imdbId)
    }

    fun getAllSavedMoviesShortMovieInfos() =
        Transformations.map(savedMoviesDao.getAllMoviesLiveData()) {
            it.toListOfDomainShortMovieInfos()
        }

    fun getIsMovieSavedInDatabase(imdbId: String) = savedMoviesDao.isMovieSavedLiveData(imdbId)

    suspend fun clearSavedMovies() {
        savedMoviesDao.clear()
    }

    private suspend fun getMovieInfoFromDatabase(imdbId: String) {
        val movieInfo = savedMoviesDao.get(imdbId)
        _requestedMovieInfo.postValue(movieInfo.toDomainAllMovieInfo())
    }

    private suspend fun getMovieInfoFromNetwork(imdbId: String) {
        val movieInfo = OmdbApi.service.getMovieByImdbId(imdbId)
        _requestedMovieInfo.postValue(movieInfo.toDomainAllMovieInfo())
    }

    private suspend fun refreshMovieInfoInDatabase(imdbId: String) {
        val freshMovieInfo = OmdbApi.service.getMovieByImdbId(imdbId)
        savedMoviesDao.update(freshMovieInfo.toDatabaseMovieInfo())
    }

    suspend fun searchMoviesInNetwork(query: String): List<DomainShortMovieInfo> {
        try {
            val searchCallback = OmdbApi.service.getMoviesByQuery(query)
            return searchCallback.searchResult!!.toListOfDomainShortMovieInfos()
        } catch (uhe: UnknownHostException) {
            _occurredException.postValue(UnknownHostException("Connection error."))
        } catch (jde: JsonDataException) {
            _occurredException.postValue(JsonDataException("Nothing have been found."))
        } catch (e: Exception) {
            _occurredException.postValue(Exception("An error occured."))
        }

        return listOf()
    }

    suspend fun getMovieInfo(imdbId: String) {
        try {
            if (savedMoviesDao.isMovieSaved(imdbId)) {
                getMovieInfoFromDatabase(imdbId)
                refreshMovieInfoInDatabase(imdbId)
                getMovieInfoFromDatabase(imdbId)
            } else {
                getMovieInfoFromNetwork(imdbId)
            }
        } catch (uhe: UnknownHostException) {
            _occurredException.postValue(UnknownHostException("Connection error."))
        } catch (e: Exception) {
            _occurredException.postValue(Exception("An error occured."))
        }
    }

    suspend fun refreshSavedMovies() {
        val savedMovies = savedMoviesDao.getAllMovies()
        val freshMovieInfos = savedMovies.map { movieInfo ->
            OmdbApi.service.getMovieByImdbId(movieInfo.imdbId).toDatabaseMovieInfo()
        }
        savedMoviesDao.insertAll(freshMovieInfos)
    }
}