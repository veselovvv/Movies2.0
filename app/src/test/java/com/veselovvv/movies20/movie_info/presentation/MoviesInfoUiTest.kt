package com.veselovvv.movies20.movie_info.presentation

import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.movie_info.presentation.BaseMoviesInfoDomainToUiMapperTest.FakeResourceProvider.Base.Companion.GENERIC_ERROR_MESSAGE
import com.veselovvv.movies20.movie_info.presentation.BaseMoviesInfoDomainToUiMapperTest.FakeResourceProvider.Base.Companion.NO_CONNECTION_MESSAGE
import com.veselovvv.movies20.movie_info.presentation.BaseMoviesInfoDomainToUiMapperTest.FakeResourceProvider.Base.Companion.SERVICE_UNAVAILABLE_MESSAGE
import com.veselovvv.movies20.movie_info.presentation.FakeMovieInfoDomainToUiMapper.Companion.MOVIE_INFO_MAP_UI
import com.veselovvv.movies20.movie_info.presentation.FakeMoviesInfoCommunication.Companion.MOVIES_INFO_COMMUNICATION_MAP
import org.junit.Before
import org.junit.Test

class MoviesInfoUiTest {
    private lateinit var order: Order
    private lateinit var movieInfoMapper: FakeMovieInfoDomainToUiMapper
    private lateinit var communication: FakeMoviesInfoCommunication

    @Before
    fun setup() {
        order = Order()
        movieInfoMapper = FakeMovieInfoDomainToUiMapper.Base(order)
        communication = FakeMoviesInfoCommunication.Base(order)
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

        val ui = MoviesInfoUi.Success(
            movieInfo = movieInfo,
            movieInfoMapper = movieInfoMapper
        )
        ui.map(mapper = communication)

        val expected = MovieInfoUi.Base(
            budget = "$100.000",
            overview = "Some overview here",
            posterPath = "somePath",
            releaseDate = "01.01.2002",
            revenue = "$10.000",
            runtime = "90",
            title = "Star Wars: Episode II - Attack of the Clones",
            rating = "4.9"
        )
        communication.checkMovieInfo(expected)
        movieInfoMapper.checkMapCalledCount(1)
        communication.checkMapCalledCount(1)
        order.check(listOf(MOVIE_INFO_MAP_UI, MOVIES_INFO_COMMUNICATION_MAP))
    }

    @Test
    fun test_fail() {
        var ui = MoviesInfoUi.Fail(message = NO_CONNECTION_MESSAGE)
        ui.map(mapper = communication)

        communication.checkMovieInfo(MovieInfoUi.Fail(message = NO_CONNECTION_MESSAGE))
        movieInfoMapper.checkMapCalledCount(0)
        communication.checkMapCalledCount(1)
        order.check(listOf(MOVIES_INFO_COMMUNICATION_MAP))

        ui = MoviesInfoUi.Fail(message = SERVICE_UNAVAILABLE_MESSAGE)
        ui.map(mapper = communication)

        communication.checkMovieInfo(MovieInfoUi.Fail(message = SERVICE_UNAVAILABLE_MESSAGE))
        movieInfoMapper.checkMapCalledCount(0)
        communication.checkMapCalledCount(2)
        order.check(listOf(MOVIES_INFO_COMMUNICATION_MAP, MOVIES_INFO_COMMUNICATION_MAP))

        ui = MoviesInfoUi.Fail(message = GENERIC_ERROR_MESSAGE)
        ui.map(mapper = communication)

        communication.checkMovieInfo(MovieInfoUi.Fail(message = GENERIC_ERROR_MESSAGE))
        movieInfoMapper.checkMapCalledCount(0)
        communication.checkMapCalledCount(3)
        order.check(
            listOf(
                MOVIES_INFO_COMMUNICATION_MAP,
                MOVIES_INFO_COMMUNICATION_MAP,
                MOVIES_INFO_COMMUNICATION_MAP
            )
        )
    }
}