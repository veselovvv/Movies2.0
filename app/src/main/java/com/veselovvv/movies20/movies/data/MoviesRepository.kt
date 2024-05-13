package com.veselovvv.movies20.movies.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.veselovvv.movies20.movies.data.cloud.MoviesCloudDataSource
import com.veselovvv.movies20.movies.data.cloud.MoviesCloudMapper
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun fetchMovies(): Flow<PagingData<MovieData>>

    class Base(
        private val cloudDataSource: MoviesCloudDataSource,
        private val cloudMapper: MoviesCloudMapper
    ) : MoviesRepository {
        override suspend fun fetchMovies(): Flow<PagingData<MovieData>> {
            return Pager(
                config = PagingConfig(pageSize = MAX_PAGE_SIZE, prefetchDistance = 2),
                pagingSourceFactory = {
                    MoviesPagingSource(cloudDataSource, cloudMapper)
                }
            ).flow
        }
    }

    companion object {
        private const val MAX_PAGE_SIZE = 10
    }
}