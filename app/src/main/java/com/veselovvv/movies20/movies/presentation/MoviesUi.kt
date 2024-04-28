package com.veselovvv.movies20.movies.presentation

import com.veselovvv.movies20.core.Object
import com.veselovvv.movies20.movies.domain.MovieDomain
import com.veselovvv.movies20.movies.domain.MovieDomainToUiMapper

sealed class MoviesUi : Object<Unit, MoviesCommunication> {
    data class Success(
        private val movies: List<MovieDomain>,
        private val movieMapper: MovieDomainToUiMapper
    ) : MoviesUi() {
        override fun map(mapper: MoviesCommunication) {
            if (movies.isEmpty()) {
                mapper.map(listOf(MovieUi.NoResults))
            } else {
                val moviesUi = movies.map { movie -> movie.map(movieMapper) }
                mapper.map(moviesUi)
            }
        }
    }

    data class Fail(private val errorMessage: String) : MoviesUi() {
        override fun map(mapper: MoviesCommunication) =
            mapper.map(listOf(MovieUi.Fail(errorMessage)))
    }
}