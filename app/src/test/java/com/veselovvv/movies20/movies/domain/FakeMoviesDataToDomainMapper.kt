package com.veselovvv.movies20.movies.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.movies.data.MovieData
import com.veselovvv.movies20.movies.data.MovieDataToDomainMapper
import com.veselovvv.movies20.movies.data.MoviesDataToDomainMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.junit.Assert.assertEquals

interface FakeMoviesDataToDomainMapper : MoviesDataToDomainMapper {
    companion object {
        const val MOVIES_MAP_DOMAIN = "FakeMoviesDataToDomainMapper#map"
    }

    fun checkMapCalledCount(count: Int)

    class Base(
        private val order: Order,
        private val movieMapper: MovieDataToDomainMapper
    ) : FakeMoviesDataToDomainMapper {
        private var mapCalledCount = 0

        override fun checkMapCalledCount(count: Int) {
            assertEquals(count, mapCalledCount)
        }

        override fun map(movies: Flow<PagingData<MovieData>>): Flow<PagingData<MovieDomain>> {
            mapCalledCount++
            order.add(MOVIES_MAP_DOMAIN)

            return movies.map { pagingData ->
                pagingData.map { movieData ->
                    movieData.map(movieMapper)
                }
            }
        }
    }
}