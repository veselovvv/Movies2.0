package com.veselovvv.movies20.movies.domain

import com.veselovvv.movies20.movies.data.MoviesDataToDomainMapper
import com.veselovvv.movies20.movies.data.MoviesRepository

interface SearchMoviesUseCase {
    suspend fun execute(query: String): MoviesDomain

    class Base(
        private val repository: MoviesRepository,
        private val mapper: MoviesDataToDomainMapper
    ) : SearchMoviesUseCase {
        override suspend fun execute(query: String) = repository.searchMovies(query).map(mapper)
    }
}