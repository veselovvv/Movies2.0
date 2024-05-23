package com.veselovvv.movies20.movie_info.domain

import com.veselovvv.movies20.core.Order
import junit.framework.Assert.assertEquals

interface FakeMovieInfoRepository : MovieInfoRepository {
    companion object {
        const val REPOSITORY_FETCH_MOVIE_INFO = "FakeMovieInfoRepository#fetchMovieInfo"
    }

    fun expectSuccess()
    fun expectFail(exception: Exception)
    fun checkCalledCount(count: Int)

    class Base(private val order: Order) : FakeMovieInfoRepository {
        private var exception: Exception? = null
        private var mapCalledCount = 0

        override fun expectSuccess() {
            exception = null
        }

        override fun expectFail(exception: Exception) {
            this.exception = exception
        }

        override fun checkCalledCount(count: Int) {
            assertEquals(count, mapCalledCount)
        }

        override suspend fun fetchMovieInfo(id: Int): MoviesInfoData {
            mapCalledCount++
            order.add(REPOSITORY_FETCH_MOVIE_INFO)

            return if (exception == null) MoviesInfoData.Success(
                movieInfo = MovieInfoData(
                    budget = 100000,
                    overview = "Some overview here",
                    posterPath = "somePath",
                    releaseDate = "2002-01-01",
                    revenue = 10000L,
                    runtime = 90,
                    title = "Star Wars: Episode II - Attack of the Clones",
                    rating = 4.9
                )
            ) else MoviesInfoData.Fail(exception = exception)
        }
    }
}