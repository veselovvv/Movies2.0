package com.veselovvv.movies20.movies.domain

import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.movies.data.MovieData
import com.veselovvv.movies20.movies.data.MovieDataToDomainMapper
import com.veselovvv.movies20.movies.data.MoviesDataToDomainMapper
import org.junit.Assert.assertEquals
import retrofit2.HttpException
import java.net.UnknownHostException

interface FakeMoviesDataToDomainMapper : MoviesDataToDomainMapper {
    companion object {
        const val MOVIES_MAP_DOMAIN_SUCCESS = "FakeMoviesDataToDomainMapper#mapSuccess"
        const val MOVIES_MAP_DOMAIN_FAIL = "FakeMoviesDataToDomainMapper#mapFail"
    }

    fun checkMapSuccessCalledCount(count: Int)
    fun checkMapFailCalledCount(count: Int)

    class Base(
        private val order: Order,
        private val movieMapper: MovieDataToDomainMapper
    ) : FakeMoviesDataToDomainMapper {
        private var mapSuccessCalledCount = 0
        private var mapFailCalledCount = 0

        override fun checkMapSuccessCalledCount(count: Int) {
            assertEquals(count, mapSuccessCalledCount)
        }

        override fun checkMapFailCalledCount(count: Int) {
            assertEquals(count, mapFailCalledCount)
        }

        override fun map(movies: List<MovieData>): MoviesDomain {
            mapSuccessCalledCount++
            order.add(MOVIES_MAP_DOMAIN_SUCCESS)
            return MoviesDomain.Success(movies, movieMapper)
        }

        override fun map(exception: Exception): MoviesDomain {
            mapFailCalledCount++
            order.add(MOVIES_MAP_DOMAIN_FAIL)

            return MoviesDomain.Fail(
                when (exception) {
                    is UnknownHostException -> ErrorType.NO_CONNECTION
                    is HttpException -> ErrorType.SERVICE_UNAVAILABLE
                    else -> ErrorType.GENERIC_ERROR
                }
            )
        }
    }
}