package com.veselovvv.movies20.movies.domain

import com.veselovvv.movies20.movies.data.MoviesDataToDomainMapper
import com.veselovvv.movies20.movies.data.MoviesRepository

interface FetchMoviesUseCase {
    suspend fun execute(): MoviesDomain

    class Base(
        private val repository: MoviesRepository,
        private val mapper: MoviesDataToDomainMapper
    ) : FetchMoviesUseCase {
        override suspend fun execute() = repository.fetchMovies().map(mapper)
    }
}