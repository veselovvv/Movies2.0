package com.veselovvv.movies20.movies.presentation

import com.veselovvv.movies20.core.FakeResourceProvider
import org.junit.Assert.assertEquals
import org.junit.Test

class BaseMoviesDomainToUiMapperTest {
    private val movieMapper = BaseMovieDomainToUiMapper()
    private val mapper = BaseMoviesDomainToUiMapper(
        resourceProvider = FakeResourceProvider(),
        movieMapper = movieMapper
    )

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
    }

    @Test
    fun test_fail() {
        var expected = MoviesUi.Fail(errorMessage = NO_CONNECTION_MESSAGE)
        var actual = mapper.map(error = ErrorType.NO_CONNECTION)
        assertEquals(expected, actual)

        expected = MoviesUi.Fail(errorMessage = SERVICE_UNAVAILABLE_MESSAGE)
        actual = mapper.map(error = ErrorType.SERVICE_UNAVAILABLE)
        assertEquals(expected, actual)
    }

    companion object {
        private const val NO_CONNECTION_MESSAGE = "No connection. Please try again!"
        private const val SERVICE_UNAVAILABLE_MESSAGE = "Service unavailable. Please try again!"
    }
}