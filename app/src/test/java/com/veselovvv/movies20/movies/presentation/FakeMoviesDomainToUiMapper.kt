package com.veselovvv.movies20.movies.presentation

import androidx.paging.PagingData
import androidx.paging.map
import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.movies.domain.MovieDomain
import com.veselovvv.movies20.movies.domain.MovieDomainToUiMapper
import com.veselovvv.movies20.movies.domain.MoviesDomainToUiMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.junit.Assert.assertEquals

interface FakeMoviesDomainToUiMapper : MoviesDomainToUiMapper {
    companion object {
        const val MOVIES_MAP_UI = "FakeMoviesDomainToUiMapper#map"
    }

    fun checkMapCalledCount(count: Int)

    class Base(
        private val order: Order,
        private val movieMapper: MovieDomainToUiMapper
    ) : FakeMoviesDomainToUiMapper {
        private var mapCalledCount = 0

        override fun checkMapCalledCount(count: Int) {
            assertEquals(count, mapCalledCount)
        }

        override fun map(movies: Flow<PagingData<MovieDomain>>): Flow<PagingData<MovieUi>> {
            mapCalledCount++
            order.add(MOVIES_MAP_UI)

            return movies.map { pagingData ->
                pagingData.map { movieDomain ->
                    movieDomain.map(movieMapper)
                }
            }
        }
    }
}
