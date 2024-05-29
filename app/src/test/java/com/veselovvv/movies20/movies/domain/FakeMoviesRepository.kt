package com.veselovvv.movies20.movies.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.movies.data.FakeMoviesCloudDataSource
import com.veselovvv.movies20.movies.data.FakeMoviesCloudMapper
import com.veselovvv.movies20.movies.data.MovieData
import com.veselovvv.movies20.movies.data.MoviesPagingSource
import com.veselovvv.movies20.movies.data.MoviesRepository
import kotlinx.coroutines.flow.Flow
import org.junit.Assert.assertEquals

interface FakeMoviesRepository : MoviesRepository {
    companion object {
        const val REPOSITORY_FETCH_MOVIES = "FakeMoviesRepository#fetchMovies"
    }

    fun checkFetchMoviesCalledCount(count: Int)

    class Base(
        private val order: Order,
        private val cloudDataSource: FakeMoviesCloudDataSource,
        private val cloudMapper: FakeMoviesCloudMapper
    ) : FakeMoviesRepository {
        private var fetchMoviesCalledCount = 0

        override fun checkFetchMoviesCalledCount(count: Int) {
            assertEquals(count, fetchMoviesCalledCount)
        }

        override suspend fun fetchMovies(): Flow<PagingData<MovieData>> {
            fetchMoviesCalledCount++
            order.add(REPOSITORY_FETCH_MOVIES)

            return Pager(
                config = PagingConfig(pageSize = MAX_PAGE_SIZE, prefetchDistance = 2),
                pagingSourceFactory = {
                    MoviesPagingSource(cloudDataSource, cloudMapper)
                }
            ).flow
        }

        companion object {
            private const val MAX_PAGE_SIZE = 10
        }
    }
}
