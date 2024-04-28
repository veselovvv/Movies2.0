package com.veselovvv.movies20.movies.presentation

import com.veselovvv.movies20.core.ErrorType
import com.veselovvv.movies20.core.FakeResourceProvider
import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.movies.domain.MovieDomain
import com.veselovvv.movies20.movies.domain.MoviesDomainToUiMapper
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BaseMoviesDomainToUiMapperTest {
    private lateinit var order: Order
    private lateinit var movieMapper: FakeMovieDomainToUiMapper
    private lateinit var mapper: MoviesDomainToUiMapper

    @Before
    fun setup() {
        order = Order()
        movieMapper = FakeMovieDomainToUiMapper.Base(order)
        mapper = BaseMoviesDomainToUiMapper(
            resourceProvider = FakeResourceProvider(),
            movieMapper = movieMapper
        )
    }

    @Test
    fun test_success() {
        val movies = listOf(
            MovieDomain(
                id = 0,
                posterPath = "somePath0",
                releaseDate = "1999",
                title = "Star Wars: Episode I - The Phantom Menace"
            ),
            MovieDomain(
                id = 1,
                posterPath = "somePath1",
                releaseDate = "2002",
                title = "Star Wars: Episode II - Attack of the Clones"
            )
        )

        val expected = MoviesUi.Success(movies = movies, movieMapper = movieMapper)
        val actual = mapper.map(movies = movies)
        assertEquals(expected, actual)
        movieMapper.checkMapCalledCount(0)
        order.check(listOf())
    }

    @Test
    fun test_fail() {
        var expected = MoviesUi.Fail(errorMessage = NO_CONNECTION_MESSAGE)
        var actual = mapper.map(error = ErrorType.NO_CONNECTION)
        assertEquals(expected, actual)
        movieMapper.checkMapCalledCount(0)
        order.check(listOf())

        expected = MoviesUi.Fail(errorMessage = SERVICE_UNAVAILABLE_MESSAGE)
        actual = mapper.map(error = ErrorType.SERVICE_UNAVAILABLE)
        assertEquals(expected, actual)
        movieMapper.checkMapCalledCount(0)
        order.check(listOf())
    }

    companion object {
        private const val NO_CONNECTION_MESSAGE = "No connection. Please try again!"
        private const val SERVICE_UNAVAILABLE_MESSAGE = "Service unavailable. Please try again!"
    }
}