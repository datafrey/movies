package com.datafrey.movies.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SavedMoviesDao {

    @Insert
    fun insert(movie: DatabaseMovieInfo)

    @Update
    fun update(movie: DatabaseMovieInfo)

    @Query("SELECT * FROM movies WHERE imdbId = :imdbId")
    fun get(imdbId: String): DatabaseMovieInfo

    @Query("DELETE FROM movies WHERE imdbId = :imdbId")
    fun delete(imdbId: String)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<DatabaseMovieInfo>>

    @Query("DELETE FROM movies")
    fun clear()
}

@Database(entities = [DatabaseMovieInfo::class], version = 1)
abstract class SavedMoviesDatabase: RoomDatabase() {
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
