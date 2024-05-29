package com.veselovvv.movies20.movies.presentation

import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.movies.domain.MovieDomainToUiMapper
import org.junit.Assert.assertEquals

interface FakeMovieDomainToUiMapper : MovieDomainToUiMapper {
    companion object {
        const val MOVIE_MAP_UI = "FakeMovieDomainToUiMapper#map"
    }

    fun checkMapCalledCount(count: Int)

    class Base(private val order: Order) : FakeMovieDomainToUiMapper {
        private var mapCalledCount = 0

        override fun checkMapCalledCount(count: Int) {
            assertEquals(count, mapCalledCount)
        }

        override fun map(
            id: Int,
            posterPath: String,
            releaseDate: String,
            title: String
        ): MovieUi {
            mapCalledCount++
            order.add(MOVIE_MAP_UI)
            return MovieUi(id, posterPath, releaseDate, title)
        }
    }
}