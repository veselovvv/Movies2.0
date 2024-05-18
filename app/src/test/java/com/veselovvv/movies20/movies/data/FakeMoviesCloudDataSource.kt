package com.veselovvv.movies20.movies.data

import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.movies.data.cloud.MovieCloud
import com.veselovvv.movies20.movies.data.cloud.MoviesCloud
import com.veselovvv.movies20.movies.data.cloud.MoviesCloudDataSource
import org.junit.Assert.assertEquals

interface FakeMoviesCloudDataSource : MoviesCloudDataSource {
    companion object {
        const val FETCH_MOVIES = "FakeMoviesCloudDataSource#fetchMovies"
    }

    fun checkCalledCount(count: Int)

    class Base(private val order: Order) : FakeMoviesCloudDataSource {
        private var calledCount = 0

        override fun checkCalledCount(count: Int) {
            assertEquals(count, calledCount)
        }

        override suspend fun fetchMovies(page: Int): MoviesCloud {
            calledCount++
            order.add(FETCH_MOVIES)

            return MoviesCloud(
                listOf(
                    MovieCloud(
                        id = 0,
                        posterPath = "somePath0",
                        releaseDate = "1999-01-01",
                        title = "Star Wars: Episode I - The Phantom Menace"
                    ),
                    MovieCloud(
                        id = 1,
                        posterPath = "somePath1",
                        releaseDate = "2002-01-01",
                        title = "Star Wars: Episode II - Attack of the Clones"
                    )
                )
            )
        }
    }
}