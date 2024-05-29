package com.veselovvv.movies20.movie_info.domain

import com.veselovvv.movies20.core.ErrorType
import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.movie_info.data.MovieInfoData
import com.veselovvv.movies20.movie_info.domain.FakeMovieInfoDataToDomainMapper.Companion.MOVIE_INFO_MAP_DOMAIN
import com.veselovvv.movies20.movie_info.presentation.BaseMoviesInfoDomainToUiMapperTest.FakeResourceProvider.Base.Companion.NO_CONNECTION_MESSAGE
import com.veselovvv.movies20.movie_info.presentation.BaseMoviesInfoDomainToUiMapperTest.FakeResourceProvider.Base.Companion.SERVICE_UNAVAILABLE_MESSAGE
import com.veselovvv.movies20.movie_info.presentation.FakeMovieInfoDomainToUiMapper
import com.veselovvv.movies20.movie_info.presentation.FakeMoviesInfoDomainToUiMapper
import com.veselovvv.movies20.movie_info.presentation.FakeMoviesInfoDomainToUiMapper.Companion.MOVIES_INFO_MAP_UI_FAIL
import com.veselovvv.movies20.movie_info.presentation.FakeMoviesInfoDomainToUiMapper.Companion.MOVIES_INFO_MAP_UI_SUCCESS
import com.veselovvv.movies20.movie_info.presentation.MoviesInfoUi
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MoviesInfoDomainTest {
    private lateinit var order: Order
    private lateinit var movieInfoDataToDomainMapper: FakeMovieInfoDataToDomainMapper
    private lateinit var movieInfoDomainToUiMapper: FakeMovieInfoDomainToUiMapper
    private lateinit var moviesInfoDomainToUiMapper: FakeMoviesInfoDomainToUiMapper

    @Before
    fun setup() {
        order = Order()
        movieInfoDataToDomainMapper = FakeMovieInfoDataToDomainMapper.Base(order)
        movieInfoDomainToUiMapper = FakeMovieInfoDomainToUiMapper.Base(order)
        moviesInfoDomainToUiMapper = FakeMoviesInfoDomainToUiMapper.Base(order, movieInfoDomainToUiMapper)
    }

    @Test
    fun test_success() {
        val movieInfoData = MovieInfoData(
            budget = 100000,
            overview = "Some overview here",
            posterPath = "somePath",
            releaseDate = "2002-01-01",
            revenue = 10000L,
            runtime = 90,
            title = "Star Wars: Episode II - Attack of the Clones",
            rating = 4.9
        )

        val domain = MoviesInfoDomain.Success(
            movieInfo = movieInfoData,
            movieInfoMapper = movieInfoDataToDomainMapper
        )

        val movieInfoDomain = MovieInfoDomain(
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
            movieInfo = movieInfoDomain,
            movieInfoMapper = movieInfoDomainToUiMapper
        )
        val actual = domain.map(mapper = moviesInfoDomainToUiMapper)
        assertEquals(expected, actual)

        movieInfoDataToDomainMapper.checkMapCalledCount(1)
        movieInfoDomainToUiMapper.checkMapCalledCount(0)
        moviesInfoDomainToUiMapper.checkMapSuccessCalledCount(1)
        moviesInfoDomainToUiMapper.checkMapFailCalledCount(0)
        order.check(listOf(MOVIE_INFO_MAP_DOMAIN, MOVIES_INFO_MAP_UI_SUCCESS))
    }

    @Test
    fun test_fail() {
        var domain = MoviesInfoDomain.Fail(errorType = ErrorType.NO_CONNECTION)
        var expected = MoviesInfoUi.Fail(message = NO_CONNECTION_MESSAGE)
        var actual = domain.map(mapper = moviesInfoDomainToUiMapper)
        assertEquals(expected, actual)

        movieInfoDataToDomainMapper.checkMapCalledCount(0)
        movieInfoDomainToUiMapper.checkMapCalledCount(0)
        moviesInfoDomainToUiMapper.checkMapSuccessCalledCount(0)
        moviesInfoDomainToUiMapper.checkMapFailCalledCount(1)
        order.check(listOf(MOVIES_INFO_MAP_UI_FAIL))

        domain = MoviesInfoDomain.Fail(errorType = ErrorType.SERVICE_UNAVAILABLE)
        expected = MoviesInfoUi.Fail(message = SERVICE_UNAVAILABLE_MESSAGE)
        actual = domain.map(mapper = moviesInfoDomainToUiMapper)
        assertEquals(expected, actual)

        movieInfoDataToDomainMapper.checkMapCalledCount(0)
        movieInfoDomainToUiMapper.checkMapCalledCount(0)
        moviesInfoDomainToUiMapper.checkMapSuccessCalledCount(0)
        moviesInfoDomainToUiMapper.checkMapFailCalledCount(2)
        order.check(listOf(MOVIES_INFO_MAP_UI_FAIL, MOVIES_INFO_MAP_UI_FAIL))
    }
}