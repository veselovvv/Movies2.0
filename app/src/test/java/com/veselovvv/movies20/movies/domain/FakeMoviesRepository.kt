package com.veselovvv.movies20.movies.domain

import com.veselovvv.movies20.core.Order
import org.junit.Assert.assertEquals

interface FakeMoviesRepository : MoviesRepository {
    companion object {
        const val REPOSITORY_FETCH = "FakeMoviesRepository#fetchMovies"
        const val REPOSITORY_SEARCH = "FakeMoviesRepository#searchMovies"
    }

    fun expectSuccess()
    fun expectFail(exception: Exception)
    fun checkFetchMoviesCalledCount(count: Int)
    fun checkSearchMoviesCalledCount(count: Int)

    class Base(private val order: Order) : FakeMoviesRepository {
        private var exception: Exception? = null
        private var fetchMoviesCalledCount = 0
        private var searchMoviesCalledCount = 0

        override fun expectSuccess() {
            exception = null
        }

        override fun expectFail(exception: Exception) {
            this.exception = exception
        }

        override fun checkFetchMoviesCalledCount(count: Int) {
            assertEquals(count, fetchMoviesCalledCount)
        }

        override fun checkSearchMoviesCalledCount(count: Int) {
            assertEquals(count, searchMoviesCalledCount)
        }

        override suspend fun fetchMovies(): MoviesData {
            fetchMoviesCalledCount++
            order.add(REPOSITORY_FETCH)

            return if (exception == null) MoviesData.Success(
                listOf(
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
            )
            else MoviesData.Fail(exception)
        }

        override suspend fun searchMovies(query: String): MoviesData {
            searchMoviesCalledCount++
            order.add(REPOSITORY_SEARCH)

            return if (exception == null) MoviesData.Success(
                listOf(
                    MovieData(
                        id = 1,
                        posterPath = "somePath1",
                        releaseDate = "2002-01-01",
                        title = "Star Wars: Episode II - Attack of the Clones"
                    )
                )
            )
            else MoviesData.Fail(exception)
        }
    }
}