package com.veselovvv.movies20.movies.data

import com.veselovvv.movies20.core.Order
import org.junit.Assert.assertEquals

interface FakeToMovieMapper : ToMovieMapper {
    companion object {
        const val TO_MOVIE_MAP = "FakeToMovieMapper#map"
    }

    fun checkMapCalledCount(count: Int)

    class Base(private val order: Order) : FakeToMovieMapper {
        private var mapCalledCount = 0

        override fun checkMapCalledCount(count: Int) {
            assertEquals(count, mapCalledCount)
        }

        override fun map(id: Int, posterPath: String, releaseDate: String, title: String): MovieData {
            mapCalledCount++
            order.add(TO_MOVIE_MAP)
            return MovieData(id, posterPath, releaseDate, title)
        }
    }
}