package com.veselovvv.movies20.movies.data

import com.veselovvv.movies20.core.Object

data class MovieData(
    private val id: Int,
    private val posterPath: String,
    private val releaseDate: String,
    private val title: String
) : Object<MovieDomain, MovieDataToDomainMapper> {
    override fun map(mapper: MovieDataToDomainMapper) =
        mapper.map(id, posterPath, releaseDate, title)
}
