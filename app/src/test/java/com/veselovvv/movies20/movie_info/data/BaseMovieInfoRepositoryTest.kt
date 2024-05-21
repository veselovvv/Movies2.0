package com.veselovvv.movies20.movie_info.data

import com.veselovvv.movies20.core.FakeException
import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.movie_info.data.BaseMovieInfoRepositoryTest.FakeMovieInfoCloudDataSource.Companion.FETCH_MOVIE_INFO
import com.veselovvv.movies20.movie_info.data.BaseMovieInfoRepositoryTest.FakeMovieInfoCloudMapper.Companion.MOVIE_INFO_CLOUD_MAP
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class BaseMovieInfoRepositoryTest {
    private lateinit var order: Order
    private lateinit var cloudDataSource: FakeMovieInfoCloudDataSource
    private lateinit var cloudMapper: FakeMovieInfoCloudMapper
    private lateinit var repository: MovieInfoRepository

    @Before
    fun setup() {
        order = Order()
        cloudDataSource = FakeMovieInfoCloudDataSource.Base(order)
        cloudMapper = FakeMovieInfoCloudMapper.Base(order)
        repository = MovieInfoRepository.Base(
            cloudDataSource = cloudDataSource,
            cloudMapper = cloudMapper
        )
    }

    @Test
    fun test_fetch_movie_info_success() = runBlocking {
        cloudDataSource.expectSuccess()

        val expected = MoviesInfoData.Success(
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
        )
        val actual = repository.fetchMovieInfo(id = 1)
        assertEquals(expected, actual)
        cloudDataSource.checkCalledCount(1)
        cloudMapper.checkCalledCount(1)
        order.check(listOf(FETCH_MOVIE_INFO, MOVIE_INFO_CLOUD_MAP))
    }

    @Test
    fun test_fetch_movie_info_fail() = runBlocking {
        cloudDataSource.expectFail()

        val expected = MoviesInfoData.Fail(exception = FakeException("Something went wrong"))
        val actual = repository.fetchMovieInfo(id = 1)
        assertEquals(expected, actual)
        cloudDataSource.checkCalledCount(1)
        cloudMapper.checkCalledCount(0)
        order.check(listOf(FETCH_MOVIE_INFO))
    }

    interface FakeMovieInfoCloudDataSource : MovieInfoCloudDataSource {
        companion object {
            const val FETCH_MOVIE_INFO = "FakeMovieInfoCloudDataSource#fetchMovieInfo"
        }

        fun expectSuccess()
        fun expectFail()
        fun checkCalledCount(count: Int)

        class Base(private val order: Order) : FakeMovieInfoCloudDataSource {
            private var success = true
            private var calledCount = 0

            override fun expectSuccess() {
                success = true
            }

            override fun expectFail() {
                success = false
            }

            override fun checkCalledCount(count: Int) {
                assertEquals(count, calledCount)
            }

            override suspend fun fetchMovieInfo(id: Int): MovieInfoCloud {
                calledCount++
                order.add(FETCH_MOVIE_INFO)

                return if (success) MovieInfoCloud(
                    budget = 100000,
                    overview = "Some overview here",
                    posterPath = "somePath",
                    releaseDate = "2002-01-01",
                    revenue = 10000L,
                    runtime = 90,
                    title = "Star Wars: Episode II - Attack of the Clones",
                    rating = 4.9
                ) else FakeException("Something went wrong")
            }
        }
    }

    interface FakeMovieInfoCloudMapper : MovieInfoCloudMapper {
        companion object {
            const val MOVIE_INFO_CLOUD_MAP = "FakeMovieInfoCloudMapper#map"
        }

        fun checkCalledCount(count: Int)

        class Base(private val order: Order) : FakeMovieInfoCloudMapper {
            private var calledCount = 0

            override fun checkCalledCount(count: Int) {
                assertEquals(count, calledCount)
            }

            override fun map(movieInfo: MovieInfoCloud): MovieInfoData {
                calledCount++
                order.add(MOVIE_INFO_CLOUD_MAP)

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
}