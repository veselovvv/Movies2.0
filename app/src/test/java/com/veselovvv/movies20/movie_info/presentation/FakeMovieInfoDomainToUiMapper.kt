package com.veselovvv.movies20.movie_info.presentation

import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.movie_info.domain.MovieInfoDomainToUiMapper
import junit.framework.Assert.assertEquals

interface FakeMovieInfoDomainToUiMapper : MovieInfoDomainToUiMapper {
    companion object {
        const val MOVIE_INFO_MAP_UI = "FakeMovieInfoDomainToUiMapper#map"
    }

    fun checkMapCalledCount(count: Int)

    class Base(private val order: Order) : FakeMovieInfoDomainToUiMapper {
        private var mapCalledCount = 0

        override fun checkMapCalledCount(count: Int) {
            assertEquals(count, mapCalledCount)
        }

        override fun map(
            budget: Int,
            overview: String,
            posterPath: String,
            releaseDate: String,
            revenue: Long,
            runtime: Int,
            title: String,
            rating: Double
        ): MovieInfoUi {
            mapCalledCount++
            order.add(MOVIE_INFO_MAP_UI)

            return MovieInfoUi.Base(
                budget = "$100.000",
                overview = "Some overview here",
                posterPath = "somePath",
                releaseDate = "01.01.2002",
                revenue = "$10.000",
                runtime = "90",
                title = "Star Wars: Episode II - Attack of the Clones",
                rating = "4.9"
            )
        }

        override fun formatPrice(value: String) = "$100.000"
        override fun formatDate(value: String) = "01.01.2002"
    }
}