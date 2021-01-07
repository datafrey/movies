package com.datafrey.movies.adapters

import com.datafrey.movies.database.DatabaseMovieInfo
import com.datafrey.movies.domain.DomainAllMovieInfo
import com.datafrey.movies.domain.DomainShortMovieInfo
import com.datafrey.movies.network.NetworkAllMovieInfo
import com.datafrey.movies.network.NetworkShortMovieInfo

fun NetworkShortMovieInfo.toDomainShortMovieInfo(): DomainShortMovieInfo {
    return DomainShortMovieInfo(
        imdbId = this.imdbId,
        posterUrl = this.posterUrl,
        title = this.title,
        type = this.type,
        year = this.year
    )
}

fun NetworkAllMovieInfo.toDomainAllMovieInfo(): DomainAllMovieInfo {
    return DomainAllMovieInfo(
        imdbId = this.imdbId,
        actors = this.actors,
        awards = this.awards,
        country = this.country,
        director = this.director,
        genre = this.genre,
        language = this.language,
        plot = this.plot,
        posterUrl = this.posterUrl,
        rated = this.rated,
        released = this.released,
        runtime = this.runtime,
        title = this.title,
        type = this.type,
        writer = this.writer,
        year = this.year
    )
}

fun DatabaseMovieInfo.toDomainShortMovieInfo(): DomainShortMovieInfo {
    return DomainShortMovieInfo(
        imdbId = this.imdbId,
        posterUrl = this.posterUrl,
        title = this.title,
        type = this.type,
        year = this.year
    )
}

fun DatabaseMovieInfo.toDomainAllMovieInfo(): DomainAllMovieInfo {
    return DomainAllMovieInfo(
        imdbId = this.imdbId,
        actors = this.actors,
        awards = this.awards,
        country = this.country,
        director = this.director,
        genre = this.genre,
        language = this.language,
        plot = this.plot,
        posterUrl = this.posterUrl,
        rated = this.rated,
        released = this.released,
        runtime = this.runtime,
        title = this.title,
        type = this.type,
        writer = this.writer,
        year = this.year
    )
}

fun NetworkAllMovieInfo.toDatabaseMovieInfo(): DatabaseMovieInfo {
    return DatabaseMovieInfo(
        imdbId = this.imdbId,
        actors = this.actors,
        awards = this.awards,
        country = this.country,
        director = this.director,
        genre = this.genre,
        language = this.language,
        plot = this.plot,
        posterUrl = this.posterUrl,
        rated = this.rated,
        released = this.released,
        runtime = this.runtime,
        title = this.title,
        type = this.type,
        writer = this.writer,
        year = this.year
    )
}

@JvmName("toDomainShortMovieInfosNetworkShortMovieInfo")
fun List<NetworkShortMovieInfo>.toDomainShortMovieInfos(): List<DomainShortMovieInfo> {
    return map { it.toDomainShortMovieInfo() }
}

@JvmName("toDomainShortMovieInfosDatabaseMovieInfo")
fun List<DatabaseMovieInfo>.toDomainShortMovieInfos(): List<DomainShortMovieInfo> {
    return map { it.toDomainShortMovieInfo() }
}
