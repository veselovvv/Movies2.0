package com.veselovvv.movies20.movies.presentation

import androidx.paging.PagingData
import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.movies.domain.FakeMoviesDataToDomainMapper
import com.veselovvv.movies20.movies.domain.FakeMoviesRepository
import com.veselovvv.movies20.movies.domain.FetchMoviesUseCase
import com.veselovvv.movies20.movies.domain.MovieDomain
import kotlinx.coroutines.flow.Flow
import org.junit.Assert.assertEquals

interface FakeFetchMoviesUseCase : FetchMoviesUseCase {
    companion object {
        const val FETCH_MOVIES_EXECUTE = "FakeFetchMoviesUseCase#execute"
    }

    fun checkCalledCount(count: Int)

    class Base(
        private val order: Order,
        private val repository: FakeMoviesRepository,
        private val mapper: FakeMoviesDataToDomainMapper
    ) : FakeFetchMoviesUseCase {
        private var calledCount = 0

        override fun checkCalledCount(count: Int) {
            assertEquals(count, calledCount)
        }

        override suspend fun execute(): Flow<PagingData<MovieDomain>> {
            calledCount++
            order.add(FETCH_MOVIES_EXECUTE)

            val moviesPagingDataList = repository.fetchMovies()
            return mapper.map(moviesPagingDataList)
        }
    }
}