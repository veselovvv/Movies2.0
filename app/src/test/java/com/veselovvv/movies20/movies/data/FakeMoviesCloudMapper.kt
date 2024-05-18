package com.veselovvv.movies20.movies.data

import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.movies.data.cloud.MovieCloud
import com.veselovvv.movies20.movies.data.cloud.MoviesCloudMapper
import org.junit.Assert.assertEquals

interface FakeMoviesCloudMapper : MoviesCloudMapper {
    companion object {
        const val MOVIES_CLOUD_MAP = "FakeMoviesCloudMapper#map"
    }

    fun checkMapCalledCount(count: Int)

    class Base(private val order: Order) : FakeMoviesCloudMapper {
        private var mapCalledCount = 0

        override fun checkMapCalledCount(count: Int) {
            assertEquals(count, mapCalledCount)
        }

        override fun map(movies: List<MovieCloud>): List<MovieData> {
            mapCalledCount++
            order.add(MOVIES_CLOUD_MAP)

            return movies.map { movie ->
                movie.map(FakeToMovieMapper.Base(order))
            }
        }
    }
}