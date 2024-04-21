package com.veselovvv.movies20.movies.presentation

import com.veselovvv.movies20.core.ErrorType
import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.movies.domain.MovieDomain
import com.veselovvv.movies20.movies.domain.MovieDomainToUiMapper
import com.veselovvv.movies20.movies.domain.MoviesDomainToUiMapper
import org.junit.Assert.assertEquals

interface FakeMoviesDomainToUiMapper : MoviesDomainToUiMapper {
    companion object {
        const val MOVIES_MAP_UI_SUCCESS = "FakeMoviesDomainToUiMapper#mapsuccess"
        const val MOVIES_MAP_UI_FAIL = "FakeMoviesDomainToUiMapper#mapfail"
    }

    fun checkSuccessMapCalledCount(count: Int)
    fun checkFailMapCalledCount(count: Int)

    class Base(
        private val order: Order,
        private val movieMapper: MovieDomainToUiMapper
    ) : FakeMoviesDomainToUiMapper {
        private var successMapCalledCount = 0
        private var failMapCalledCount = 0

        override fun checkSuccessMapCalledCount(count: Int) {
            assertEquals(count, successMapCalledCount)
        }

        override fun checkFailMapCalledCount(count: Int) {
            assertEquals(count, failMapCalledCount)
        }

        override fun map(movies: List<MovieDomain>): MoviesUi {
            successMapCalledCount++
            order.add(MOVIES_MAP_UI_SUCCESS)
            return MoviesUi.Success(movies, movieMapper)
        }

        override fun map(error: ErrorType): MoviesUi {
            failMapCalledCount++
            order.add(MOVIES_MAP_UI_FAIL)

            return MoviesUi.Fail(
                when (error) {
                    ErrorType.NO_CONNECTION -> NO_CONNECTION_MESSAGE
                    ErrorType.SERVICE_UNAVAILABLE -> SERVICE_UNAVAILABLE_MESSAGE
                    else -> SOMETHING_WENT_WRONG
                }
            )
        }

        companion object {
            private const val NO_CONNECTION_MESSAGE = "No connection. Please try again!"
            private const val SERVICE_UNAVAILABLE_MESSAGE = "Service unavailable. Please try again!"
            private const val SOMETHING_WENT_WRONG = "Something went wrong. Please try again!"
        }
    }
}