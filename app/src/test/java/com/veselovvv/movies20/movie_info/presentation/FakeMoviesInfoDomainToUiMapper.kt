package com.veselovvv.movies20.movie_info.presentation

import com.veselovvv.movies20.core.ErrorType
import com.veselovvv.movies20.core.Order
import junit.framework.Assert.assertEquals

interface FakeMoviesInfoDomainToUiMapper : MoviesInfoDomainToUiMapper {
    companion object {
        const val MOVIES_INFO_MAP_UI_SUCCESS = "FakeMoviesInfoDomainToUiMapper#mapSuccess"
        const val MOVIES_INFO_MAP_UI_FAIL = "FakeMoviesInfoDomainToUiMapper#mapFail"
    }

    fun checkMapSuccessCalledCount(count: Int)
    fun checkMapFailCalledCount(count: Int)

    class Base(private val order: Order) : FakeMoviesInfoDomainToUiMapper {
        private var mapSuccessCalledCount = 0
        private var mapFailCalledCount = 0

        override fun checkMapSuccessCalledCount(count: Int) {
            assertEquals(count, mapSuccessCalledCount)
        }

        override fun checkMapFailCalledCount(count: Int) {
            assertEquals(count, mapFailCalledCount)
        }

        override fun map(movieInfo: MovieInfoDomain): MoviesInfoUi {
            mapSuccessCalledCount++
            order.add(MOVIES_INFO_MAP_UI_SUCCESS)

            return MoviesInfoUi.Success(
                movieInfo = MovieInfoDomain(
                    budget = 100000,
                    overview = "Some overview here",
                    posterPath = "somePath",
                    releaseDate = "2002-01-01",
                    revenue = 10000L,
                    runtime = 90,
                    title = "Star Wars: Episode II - Attack of the Clones",
                    rating = 4.9
                ),
                movieInfoMapper = BaseMovieInfoDomainToUiMapper()
            )
        }

        override fun map(errorType: ErrorType): MoviesInfoUi {
            mapFailCalledCount++
            order.add(MOVIES_INFO_MAP_UI_FAIL)

            return MoviesInfoUi.Fail(
                message = when (errorType) {
                    ErrorType.NO_CONNECTION -> NO_CONNECTION_MESSAGE
                    ErrorType.SERVICE_UNAVAILABLE -> SERVICE_UNAVAILABLE_MESSAGE
                    else -> GENERIC_ERROR_MESSAGE
                }
            )
        }

        companion object {
            const val NO_CONNECTION_MESSAGE = "No connection. Please try again!"
            const val SERVICE_UNAVAILABLE_MESSAGE = "Service unavailable. Please try again!"
            const val GENERIC_ERROR_MESSAGE = "Something went wrong. Please try again!"
        }
    }
}