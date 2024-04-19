package com.veselovvv.movies20.movies.presentation

import com.veselovvv.movies20.core.Order
import org.junit.Assert.assertEquals

interface FakeMovieCache : MovieCache.Mutable {
    fun checkSaveCalledCount(count: Int)
    fun checkReadCalledCount(count: Int)

    class Base(private val order: Order) : FakeMovieCache {
        companion object {
            const val SAVE_MOVIE_INFO = "FakeMovieCache#save"
            const val READ_MOVIE_INFO = "FakeMovieCache#read"
        }

        private var saveCalledCount = 0
        private var readCalledCount = 0

        override fun checkSaveCalledCount(count: Int) {
            assertEquals(count, saveCalledCount)
        }

        override fun checkReadCalledCount(count: Int) {
            assertEquals(count, readCalledCount)
        }

        override fun save(data: MovieParameters) {
            saveCalledCount++
            order.add(SAVE_MOVIE_INFO)
        }

        override fun read(): MovieParameters {
            readCalledCount++
            order.add(READ_MOVIE_INFO)

            return MovieParameters(
                id = 1,
                posterPath = "somePath1",
                title = "Star Wars: Episode II - Attack of the Clones"
            )
        }
    }
}