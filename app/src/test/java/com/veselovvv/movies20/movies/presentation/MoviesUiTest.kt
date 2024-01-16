package com.veselovvv.movies20.movies.presentation

import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.movies.presentation.FakeMoviesCommunication.Companion.MAP
import org.junit.Before
import org.junit.Test

class MoviesUiTest {
    private lateinit var order: Order
    private lateinit var communication: FakeMoviesCommunication

    @Before
    fun setup() {
        order = Order()
        communication = FakeMoviesCommunication.Base(order)
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

        var ui = MoviesUi.Success(movies = movies, movieMapper = BaseMovieDomainToUiMapper())
        ui.map(mapper = communication)

        communication.checkList(
            listOf<MovieUi>(
                MovieUi.Base(
                    id = 0,
                    posterPath = "somePath0",
                    releaseDate = "1999",
                    title = "Star Wars: Episode I - The Phantom Menace"
                ),
                MovieUi.Base(
                    id = 1,
                    posterPath = "somePath1",
                    releaseDate = "2002",
                    title = "Star Wars: Episode II - Attack of the Clones"
                )
            )
        )
        communication.checkMapCalledCount(1)
        order.check(listOf(MAP))

        ui = MoviesUi.Success(movies = listOf(), movieMapper = BaseMovieDomainToUiMapper())
        ui.map(mapper = communication)

        communication.checkList(listOf<MovieUi>(MovieUi.NoResults))
        communication.checkMapCalledCount(2)
        order.check(listOf(MAP, MAP))
    }

    @Test
    fun test_fail() {
        val ui = MoviesUi.Fail(errorMessage = GENERIC_ERROR_MESSAGE)
        ui.map(mapper = communication)

        communication.checkList(listOf<MovieUi>(MovieUi.Fail(errorMessage = GENERIC_ERROR_MESSAGE)))
        communication.checkMapCalledCount(1)
        order.check(listOf(MAP))
    }

    companion object {
        private const val GENERIC_ERROR_MESSAGE = "Something went wrong. Please try again!"
    }
}