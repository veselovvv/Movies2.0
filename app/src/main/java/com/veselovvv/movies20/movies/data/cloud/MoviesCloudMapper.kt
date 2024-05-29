package com.veselovvv.movies20.movies.data.cloud

import com.veselovvv.movies20.movies.data.MovieData
import com.veselovvv.movies20.movies.data.ToMovieMapper

interface MoviesCloudMapper {
    fun map(movies: List<MovieCloud>): List<MovieData>

    class Base(private val movieMapper: ToMovieMapper) : MoviesCloudMapper {
        override fun map(movies: List<MovieCloud>) = movies.map { movie ->
            movie.map(movieMapper)
        }
    }
}