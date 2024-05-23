package com.veselovvv.movies20.movie_info.domain

import com.veselovvv.movies20.core.ErrorType
import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.movie_info.domain.FakeMovieInfoDataToDomainMapper.Companion.MOVIE_INFO_MAP_DOMAIN
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class BaseMoviesInfoDataToDomainMapperTest {
    private lateinit var order: Order
    private lateinit var movieInfoMapper: FakeMovieInfoDataToDomainMapper
    private lateinit var mapper: MoviesInfoDataToDomainMapper

    @Before
    fun setup() {
        order = Order()
        movieInfoMapper = FakeMovieInfoDataToDomainMapper.Base(order)
        mapper = BaseMoviesInfoDataToDomainMapper(mapper = movieInfoMapper)
    }

    @Test
    fun test_success() {
        val movieInfo = MovieInfoData(
            budget = 100000,
            overview = "Some overview here",
            posterPath = "somePath",
            releaseDate = "2002-01-01",
            revenue = 10000L,
            runtime = 90,
            title = "Star Wars: Episode II - Attack of the Clones",
            rating = 4.9
        )

        val expected = MoviesInfoDomain.Success(
            movieInfo = movieInfo,
            movieInfoMapper = movieInfoMapper
        )
        val actual = mapper.map(movieInfo)

        assertEquals(expected, actual)
        movieInfoMapper.checkMapCalledCount(1)
        order.check(listOf(MOVIE_INFO_MAP_DOMAIN))
    }

    @Test
    fun test_fail() {
        var expected = MoviesInfoDomain.Fail(errorType = ErrorType.NO_CONNECTION)
        var actual = mapper.map(UnknownHostException())

        assertEquals(expected, actual)
        movieInfoMapper.checkMapCalledCount(0)
        order.check(listOf())

        expected = MoviesInfoDomain.Fail(errorType = ErrorType.GENERIC_ERROR)
        actual = mapper.map(Exception())

        assertEquals(expected, actual)
        movieInfoMapper.checkMapCalledCount(0)
        order.check(listOf())
    }
}