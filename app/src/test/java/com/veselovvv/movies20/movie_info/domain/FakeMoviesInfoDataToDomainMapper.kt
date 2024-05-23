package com.veselovvv.movies20.movie_info.domain

import com.veselovvv.movies20.core.ErrorType
import com.veselovvv.movies20.core.Order
import junit.framework.Assert.assertEquals
import retrofit2.HttpException
import java.net.UnknownHostException

interface FakeMoviesInfoDataToDomainMapper : MoviesInfoDataToDomainMapper {
    companion object {
        const val MOVIES_INFO_MAP_DOMAIN_SUCCESS = "FakeMoviesInfoDataToDomainMapper#mapSuccess"
        const val MOVIES_INFO_MAP_DOMAIN_FAIL = "FakeMoviesInfoDataToDomainMapper#mapFail"
    }

    fun checkMapSuccessCalledCount(count: Int)
    fun checkMapFailCalledCount(count: Int)

    class Base(private val order: Order) : FakeMoviesInfoDataToDomainMapper {
        private var mapSuccessCalledCount = 0
        private var mapFailCalledCount = 0

        override fun checkMapSuccessCalledCount(count: Int) {
            assertEquals(count, mapSuccessCalledCount)
        }

        override fun checkMapFailCalledCount(count: Int) {
            assertEquals(count, mapFailCalledCount)
        }

        override fun map(movieInfo: MovieInfoData): MoviesInfoDomain {
            mapSuccessCalledCount++
            order.add(MOVIES_INFO_MAP_DOMAIN_SUCCESS)

            return MoviesInfoDomain.Success(
                movieInfo = MovieInfoData(
                    budget = 100000,
                    overview = "Some overview here",
                    posterPath = "somePath",
                    releaseDate = "2002-01-01",
                    revenue = 10000L,
                    runtime = 90,
                    title = "Star Wars: Episode II - Attack of the Clones",
                    rating = 4.9
                ),
                movieInfoMapper = BaseMovieInfoDataToDomainMapper()
            )
        }

        override fun map(exception: Exception): MoviesInfoDomain {
            mapFailCalledCount++
            order.add(MOVIES_INFO_MAP_DOMAIN_FAIL)

            return MoviesInfoDomain.Fail(
                errorType = when (exception) {
                    is UnknownHostException -> ErrorType.NO_CONNECTION
                    is HttpException -> ErrorType.SERVICE_UNAVAILABLE
                    else -> ErrorType.GENERIC_ERROR
                }
            )
        }
    }
}