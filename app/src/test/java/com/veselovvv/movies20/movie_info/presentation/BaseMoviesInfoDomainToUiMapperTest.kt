package com.veselovvv.movies20.movie_info.presentation

import com.veselovvv.movies20.R
import com.veselovvv.movies20.core.ErrorType
import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.core.ResourceProvider
import com.veselovvv.movies20.movie_info.presentation.BaseMoviesInfoDomainToUiMapperTest.FakeResourceProvider.Base.Companion.NO_CONNECTION_MESSAGE
import com.veselovvv.movies20.movie_info.presentation.BaseMoviesInfoDomainToUiMapperTest.FakeResourceProvider.Base.Companion.SERVICE_UNAVAILABLE_MESSAGE
import com.veselovvv.movies20.movie_info.presentation.BaseMoviesInfoDomainToUiMapperTest.FakeResourceProvider.Companion.RESOURCE_PROVIDER_GET_STRING
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BaseMoviesInfoDomainToUiMapperTest {
    private lateinit var order: Order
    private lateinit var movieInfoMapper: FakeMovieInfoDomainToUiMapper
    private lateinit var resourceProvider: FakeResourceProvider
    private lateinit var mapper: MoviesInfoDomainToUiMapper

    @Before
    fun setup() {
        order = Order()
        movieInfoMapper = FakeMovieInfoDomainToUiMapper.Base(order)
        resourceProvider = FakeResourceProvider.Base(order)
        mapper = BaseMoviesInfoDomainToUiMapper(
            resourceProvider = resourceProvider,
            mapper = movieInfoMapper
        )
    }

    @Test
    fun test_success() {
        val movieInfo = MovieInfoDomain(
            budget = 100000,
            overview = "Some overview here",
            posterPath = "somePath",
            releaseDate = "2002-01-01",
            revenue = 10000L,
            runtime = 90,
            title = "Star Wars: Episode II - Attack of the Clones",
            rating = 4.9
        )

        val expected = MoviesInfoUi.Success(
            movieInfo = movieInfo,
            movieInfoMapper = movieInfoMapper
        )
        val actual = mapper.map(movieInfo = movieInfo)
        assertEquals(expected, actual)

        movieInfoMapper.checkMapCalledCount(0)
        resourceProvider.checkCalledCount(0)
        order.check(listOf())
    }

    @Test
    fun test_fail() {
        var expected = MoviesInfoUi.Fail(message = NO_CONNECTION_MESSAGE)
        var actual = mapper.map(errorType = ErrorType.NO_CONNECTION)
        assertEquals(expected, actual)

        movieInfoMapper.checkMapCalledCount(0)
        resourceProvider.checkCalledCount(1)
        order.check(listOf(RESOURCE_PROVIDER_GET_STRING))

        expected = MoviesInfoUi.Fail(message = SERVICE_UNAVAILABLE_MESSAGE)
        actual = mapper.map(errorType = ErrorType.SERVICE_UNAVAILABLE)
        assertEquals(expected, actual)

        movieInfoMapper.checkMapCalledCount(0)
        resourceProvider.checkCalledCount(2)
        order.check(listOf(RESOURCE_PROVIDER_GET_STRING, RESOURCE_PROVIDER_GET_STRING))
    }

    interface FakeResourceProvider : ResourceProvider {
        companion object {
            const val RESOURCE_PROVIDER_GET_STRING = "FakeResourceProvider#getString"
        }

        fun checkCalledCount(count: Int)

        class Base(private val order: Order) : FakeResourceProvider {
            private var calledCount = 0

            override fun checkCalledCount(count: Int) {
                assertEquals(count, calledCount)
            }

            override fun getString(id: Int): String {
                calledCount++
                order.add(RESOURCE_PROVIDER_GET_STRING)

                return when (id) {
                    R.string.no_connection_message -> NO_CONNECTION_MESSAGE
                    R.string.service_unavailable_message -> SERVICE_UNAVAILABLE_MESSAGE
                    else -> GENERIC_ERROR_MESSAGE
                }
            }

            companion object {
                const val NO_CONNECTION_MESSAGE = "No connection. Please try again!"
                const val SERVICE_UNAVAILABLE_MESSAGE = "Service unavailable. Please try again!"
                const val GENERIC_ERROR_MESSAGE = "Something went wrong. Please try again!"
            }
        }
    }
}