package com.veselovvv.movies20.movies.domain

import androidx.paging.PagingData
import com.veselovvv.movies20.movies.data.MoviesDataToDomainMapper
import com.veselovvv.movies20.movies.data.MoviesRepository
import kotlinx.coroutines.flow.Flow

interface FetchMoviesUseCase {
    suspend fun execute(): Flow<PagingData<MovieDomain>>

    class Base(
        private val repository: MoviesRepository,
        private val mapper: MoviesDataToDomainMapper
    ) : FetchMoviesUseCase {
        override suspend fun execute(): Flow<PagingData<MovieDomain>> {
            val moviesPagingDataList = repository.fetchMovies()
            return mapper.map(moviesPagingDataList)
        }
    }
}