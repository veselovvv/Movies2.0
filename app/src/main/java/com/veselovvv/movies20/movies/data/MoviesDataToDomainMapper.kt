package com.veselovvv.movies20.movies.data

interface MoviesDataToDomainMapper {
    fun map(movies: List<MovieData>): MoviesDomain
    fun map(exception: Exception): MoviesDomain
}