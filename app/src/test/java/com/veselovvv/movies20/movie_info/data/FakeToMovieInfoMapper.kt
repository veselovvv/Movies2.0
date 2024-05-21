package com.veselovvv.movies20.movie_info.data

import com.veselovvv.movies20.core.Order
import junit.framework.Assert.assertEquals

interface FakeToMovieInfoMapper : ToMovieInfoMapper {
    companion object {
        const val TO_MOVIE_INFO_MAP = "FakeToMovieInfoMapper#map"
    }

    fun checkMapCalledCount(count: Int)

    class Base(private val order: Order) : FakeToMovieInfoMapper {
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
        ): MovieInfoData {
            mapCalledCount++
            order.add(TO_MOVIE_INFO_MAP)
            return MovieInfoData(
                budget = 100000,
                overview = "Some overview here",
                posterPath = "somePath",
                releaseDate = "2002-01-01",
                revenue = 10000L,
                runtime = 90,
                title = "Star Wars: Episode II - Attack of the Clones",
                rating = 4.9
            )
        }
    }
}