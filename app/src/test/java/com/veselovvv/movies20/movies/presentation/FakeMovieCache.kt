package com.veselovvv.movies20.movies.presentation

import com.veselovvv.movies20.core.Order
import org.junit.Assert.assertEquals

interface FakeMovieCache : MovieCache {
    fun checkSaveCalledCount(count: Int)

    class Base(private val order: Order) : FakeMovieCache {
        companion object {
            const val SAVE_MOVIE_INFO = "FakeMovieCache#save"
        }

        private var calledCount = 0

        override fun checkSaveCalledCount(count: Int) {
            assertEquals(count, calledCount)
        }

        override fun save(data: MovieParameters) {
            calledCount++
            order.add(SAVE_MOVIE_INFO)
        }

        override fun read() = MovieParameters(
            id = 1,
            posterPath = "somePath1",
            title = "Star Wars: Episode II - Attack of the Clones"
        )
    }
}