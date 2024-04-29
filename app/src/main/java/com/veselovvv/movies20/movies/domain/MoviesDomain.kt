package com.veselovvv.movies20.movies.domain

import com.veselovvv.movies20.core.ErrorType
import com.veselovvv.movies20.core.Object
import com.veselovvv.movies20.movies.data.MovieData
import com.veselovvv.movies20.movies.data.MovieDataToDomainMapper
import com.veselovvv.movies20.movies.presentation.MoviesUi

sealed class MoviesDomain : Object<MoviesUi, MoviesDomainToUiMapper> {
    data class Success(
        private val movies: List<MovieData>,
        private val movieMapper: MovieDataToDomainMapper
    ) : MoviesDomain() {
        override fun map(mapper: MoviesDomainToUiMapper) = mapper.map(
            movies.map { movie -> movie.map(movieMapper) }
        )
    }

    data class Fail(private val error: ErrorType) : MoviesDomain() {
        override fun map(mapper: MoviesDomainToUiMapper) = mapper.map(error)
    }
}