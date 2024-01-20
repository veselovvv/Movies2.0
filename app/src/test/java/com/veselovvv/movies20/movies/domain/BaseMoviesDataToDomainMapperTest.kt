package com.veselovvv.movies20.movies.domain

import com.veselovvv.movies20.core.Order
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class BaseMoviesDataToDomainMapperTest {
    private lateinit var order: Order
    private lateinit var movieMapper: FakeMovieDataToDomainMapper
    private lateinit var mapper: MoviesDataToDomainMapper

    @Before
    fun setup() {
        order = Order()
        movieMapper = FakeMovieDataToDomainMapper.Base(order)
        mapper = BaseMoviesDataToDomainMapper(movieMapper = movieMapper)
    }

    @Test
    fun test_success() {
        val movies = listOf(
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

        val expected = MoviesDomain.Success(movies = movies, movieMapper = movieMapper)
        val actual = mapper.map(movies = movies)
        assertEquals(expected, actual)
        movieMapper.checkMapCalledCount(0)
        order.check(listOf())
    }

    @Test
    fun test_fail() {
        var expected = MoviesDomain.Fail(error = ErrorType.NO_CONNECTION)
        var actual = mapper.map(exception = UnknownHostException())
        assertEquals(expected, actual)
        movieMapper.checkMapCalledCount(0)
        order.check(listOf())

        expected = MoviesDomain.Fail(error = ErrorType.GENERIC_ERROR)
        actual = mapper.map(exception = Exception())
        assertEquals(expected, actual)
        movieMapper.checkMapCalledCount(0)
        order.check(listOf())
    }
}