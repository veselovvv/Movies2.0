package com.veselovvv.movies20.movies.presentation

import com.veselovvv.movies20.core.Order
import org.junit.Assert.assertEquals

interface FakeMoviesDomainToUiMapper : MoviesDomainToUiMapper {
    companion object {
        const val MAP_MOVIES_SUCCESS = "FakeMoviesDomainToUiMapper#mapsuccess"
        const val MAP_MOVIES_FAIL = "FakeMoviesDomainToUiMapper#mapfail"
    }

    fun checkSuccessMapCalledCount(count: Int)
    fun checkFailMapCalledCount(count: Int)

    class Base(private val order: Order) : FakeMoviesDomainToUiMapper {
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
            order.add(MAP_MOVIES_SUCCESS)
            return MoviesUi.Success(movies, BaseMovieDomainToUiMapper())
        }

        override fun map(error: ErrorType): MoviesUi {
            failMapCalledCount++
            order.add(MAP_MOVIES_FAIL)

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