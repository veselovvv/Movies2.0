package com.veselovvv.movies20.movies.domain

import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.movies.presentation.FakeMovieDomainToUiMapper
import com.veselovvv.movies20.movies.presentation.FakeMoviesDomainToUiMapper
import com.veselovvv.movies20.movies.presentation.FakeMoviesDomainToUiMapper.Companion.MAP_MOVIES_FAIL
import com.veselovvv.movies20.movies.presentation.FakeMoviesDomainToUiMapper.Companion.MAP_MOVIES_SUCCESS
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MoviesDomainTest {
    private lateinit var order: Order
    private lateinit var movieDomainToUiMapper: FakeMovieDomainToUiMapper
    private lateinit var movieDataToDomainMapper: FakeMovieDataToDomainMapper
    private lateinit var moviesDomainToUiMapper: FakeMoviesDomainToUiMapper

    @Before
    fun setup() {
        order = Order()
        movieDomainToUiMapper = FakeMovieDomainToUiMapper.Base(order)
        movieDataToDomainMapper = FakeMovieDataToDomainMapper.Base(order)
        moviesDomainToUiMapper = FakeMoviesDomainToUiMapper.Base(order)
    }

    @Test
    fun test_success() {
        val moviesDataList = listOf(
            MovieData(
                id = 0,
                posterPath = "somePath0",
                releaseDate = "1999-01-01",
                title = "Star Wars: Episode I - The Phantom Menace"
            ),
            MovieData(
                id = 1,
                posterPath = "somePath1",
                releaseDate = "2002-01-01",
                title = "Star Wars: Episode II - Attack of the Clones"
            )
        )

        val moviesDomainList = listOf(
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

        val domain = MoviesDomain.Success(
            movies = moviesDataList,
            movieMapper = movieDataToDomainMapper
        )
        val expected = MoviesUi.Success(
            movies = moviesDomainList,
            movieMapper = movieDomainToUiMapper
        )
        val actual = domain.map(mapper = moviesDomainToUiMapper)
        assertEquals(expected, actual)
        movieDomainToUiMapper.checkMapCalledCount(0)
        movieDataToDomainMapper.checkMapCalledCount(0)
        moviesDomainToUiMapper.checkSuccessMapCalledCount(1)
        moviesDomainToUiMapper.checkFailMapCalledCount(0)
        order.check(listOf(MAP_MOVIES_SUCCESS))
    }

    @Test
    fun test_fail() {
        var domain = MoviesDomain.Fail(error = ErrorType.NO_CONNECTION)
        var expected = MoviesUi.Fail(errorMessage = NO_CONNECTION_MESSAGE)
        var actual = domain.map(mapper = moviesDomainToUiMapper)
        assertEquals(expected, actual)
        movieDomainToUiMapper.checkMapCalledCount(0)
        movieDataToDomainMapper.checkMapCalledCount(0)
        moviesDomainToUiMapper.checkSuccessMapCalledCount(0)
        moviesDomainToUiMapper.checkFailMapCalledCount(1)
        order.check(listOf(MAP_MOVIES_FAIL))

        domain = MoviesDomain.Fail(error = ErrorType.SERVICE_UNAVAILABLE)
        var expected = MoviesUi.Fail(errorMessage = SERVICE_UNAVAILABLE_MESSAGE)
        var actual = domain.map(mapper = moviesDomainToUiMapper)
        assertEquals(expected, actual)
        movieDomainToUiMapper.checkMapCalledCount(0)
        movieDataToDomainMapper.checkMapCalledCount(0)
        moviesDomainToUiMapper.checkSuccessMapCalledCount(0)
        moviesDomainToUiMapper.checkFailMapCalledCount(2)
        order.check(listOf(MAP_MOVIES_FAIL, MAP_MOVIES_FAIL))
    }

    companion object {
        private const val NO_CONNECTION_MESSAGE = "No connection. Please try again!"
        private const val SERVICE_UNAVAILABLE_MESSAGE = "Service unavailable. Please try again!"
    }
}