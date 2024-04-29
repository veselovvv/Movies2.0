package com.veselovvv.movies20.movies.data

import com.veselovvv.movies20.movies.domain.MoviesDomain

interface MoviesDataToDomainMapper {
    fun map(movies: List<MovieData>): MoviesDomain
    fun map(exception: Exception): MoviesDomain
}