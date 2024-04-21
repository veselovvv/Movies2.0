package com.veselovvv.movies20.movies.data

import com.veselovvv.movies20.core.Object

sealed class MoviesData : Object<MoviesDomain, MoviesDataToDomainMapper> {
    data class Success(private val movies: List<MovieData>) : MoviesData() {
        override fun map(mapper: MoviesDataToDomainMapper) = mapper.map(movies)
    }

    data class Fail(private val exception: Exception) : MoviesData() {
        override fun map(mapper: MoviesDataToDomainMapper) = mapper.map(exception)
    }
}