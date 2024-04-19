package com.veselovvv.movies20.movies.domain

import com.veselovvv.movies20.core.Order
import org.junit.Assert.assertEquals

interface FakeMovieDataToDomainMapper : MovieDataToDomainMapper {
    companion object {
        const val MOVIE_MAP_DOMAIN = "FakeMovieDataToDomainMapper#map"
    }

    fun checkMapCalledCount(count: Int)

    class Base(private val order: Order) : FakeMovieDataToDomainMapper {
        private var mapCalledCount = 0

        override fun checkMapCalledCount(count: Int) {
            assertEquals(count, mapCalledCount)
        }

        override fun map(
            id: Int,
            posterPath: String,
            releaseDate: String,
            title: String
        ): MovieDomain {
            mapCalledCount++
            order.add(MOVIE_MAP_DOMAIN)
            return MovieDomain(id, posterPath, releaseDate.substring(0, 4), title)
        }
    }
}