package com.datafrey.movies.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SavedMoviesDao {

    @Insert
    suspend fun insert(movie: DatabaseMovieInfo)

    @Update
    suspend fun update(movie: DatabaseMovieInfo)

    @Query("SELECT * FROM movies WHERE imdbId = :imdbId")
    suspend fun get(imdbId: String): DatabaseMovieInfo

    @Query("DELETE FROM movies WHERE imdbId = :imdbId")
    suspend fun delete(imdbId: String)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<DatabaseMovieInfo>>

    @Query("SELECT COUNT(*) != 0 FROM movies WHERE imdbId = :imdbId")
    suspend fun isMovieSaved(imdbId: String): Boolean

    @Query("SELECT COUNT(*) != 0 FROM movies WHERE imdbId = :imdbId")
    fun isMovieSavedLiveData(imdbId: String): LiveData<Boolean>

    @Query("DELETE FROM movies")
    suspend fun clear()
}

@Database(entities = [DatabaseMovieInfo::class], version = 1)
abstract class SavedMoviesDatabase : RoomDatabase() {
    abstract val savedMoviesDao: SavedMoviesDao
}

private lateinit var INSTANCE: SavedMoviesDatabase

fun getSavedMoviesDatabase(context: Context): SavedMoviesDatabase {
    synchronized(SavedMoviesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                SavedMoviesDatabase::class.java,
                "savedMovies").build()
        }
    }

    return INSTANCE
}
