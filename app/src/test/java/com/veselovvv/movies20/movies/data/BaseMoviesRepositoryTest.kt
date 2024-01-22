package com.veselovvv.movies20.movies.data

import com.veselovvv.movies20.core.FakeException
import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.movies.data.BaseMoviesRepositoryTest.FakeMoviesCloudDataSource.Companion.FETCH_MOVIES
import com.veselovvv.movies20.movies.data.BaseMoviesRepositoryTest.FakeMoviesCloudMapper.Companion.MOVIES_CLOUD_MAP
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BaseMoviesRepositoryTest {
    private lateinit var order: Order
    private lateinit var cloudDataSource: FakeMoviesCloudDataSource
    private lateinit var cloudMapper: FakeMoviesCloudMapper
    private lateinit var repository: MoviesRepository

    @Before
    fun setup() {
        order = Order()
        cloudDataSource = FakeMoviesCloudDataSource.Base(order)
        cloudMapper = FakeMoviesCloudMapper.Base(order)
        repository = MoviesRepository.Base(
            cloudDataSource = cloudDataSource,
            cloudMapper = cloudMapper
        )
    }

    @Test
    fun test_fetch_movies_success() = runBlocking {
        cloudDataSource.expectSuccess()

        val expected = MoviesData.Success(
            movies = listOf(
                MovieData(
                    id = 0,
                    posterPath = "somePath0",
                    releaseDate = "1999-01-01",
                    title = "Star Wars: Episode I - The Phantom Menace"
                ),
                MovieData(
                    id = 1,
                    posterPath = "somePath1",
                    releaseDate = "2002-01-01",
                    title = "Star Wars: Episode II - Attack of the Clones"
                )
            )
        )

        val actual = repository.fetchMovies()
        assertEquals(expected, actual)
        cloudDataSource.checkCalledCount(1)
        cloudMapper.checkMapCalledCount(1)
        order.check(listOf(FETCH_MOVIES, MOVIES_CLOUD_MAP))
    }

    @Test
    fun test_fetch_movies_fail() = runBlocking {
        cloudDataSource.expectFail()

        val expected = MoviesData.Fail(exception = FakeException("Something went wrong!"))
        val actual = repository.fetchMovies()
        assertEquals(expected, actual)
        cloudDataSource.checkCalledCount(1)
        cloudMapper.checkMapCalledCount(0)
        order.check(listOf(FETCH_MOVIES))
    }

    @Test
    fun test_search_movies_success() = runBlocking {
        cloudDataSource.expectSuccess()

        var expected = MoviesData.Success(
            movies = listOf(
                MovieData(
                    id = 1,
                    posterPath = "somePath1",
                    releaseDate = "2002-01-01",
                    title = "Star Wars: Episode II - Attack of the Clones"
                )
            )
        )

        var actual = repository.searchMovies(query = "Star Wars: Episode II")
        assertEquals(expected, actual)
        cloudDataSource.checkCalledCount(1)
        cloudMapper.checkMapCalledCount(1)
        order.check(listOf(FETCH_MOVIES, MOVIES_CLOUD_MAP))

        expected = MoviesData.Success(movies = listOf())
        actual = repository.searchMovies(query = "Element that does not exist")
        assertEquals(expected, actual)
        cloudDataSource.checkCalledCount(2)
        cloudMapper.checkMapCalledCount(2)
        order.check(listOf(FETCH_MOVIES, MOVIES_CLOUD_MAP, FETCH_MOVIES, MOVIES_CLOUD_MAP))
    }

    @Test
    fun test_search_movies_fail() = runBlocking {
        cloudDataSource.expectFail()

        val expected = MoviesData.Fail(exception = FakeException("Something went wrong!"))
        val actual = repository.searchMovies(query = "Any query")
        assertEquals(expected, actual)
        cloudDataSource.checkCalledCount(1)
        cloudMapper.checkMapCalledCount(0)
        order.check(listOf(FETCH_MOVIES))
    }

    interface FakeMoviesCloudDataSource : MoviesCloudDataSource {
        companion object {
            const val FETCH_MOVIES = "FakeMoviesCloudDataSource#fetchMovies"
        }

        fun expectSuccess()
        fun expectFail()
        fun checkCalledCount(count: Int)

        class Base(private val order: Order) : FakeMoviesCloudDataSource {
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

            override suspend fun fetchMovies(): List<MovieCloud> {
                calledCount++
                order.add(FETCH_MOVIES)

                return if (success) listOf(
                    MovieCloud(
                        id = 0,
                        posterPath = "somePath0",
                        releaseDate = "1999-01-01",
                        title = "Star Wars: Episode I - The Phantom Menace"
                    ),
                    MovieCloud(
                        id = 1,
                        posterPath = "somePath1",
                        releaseDate = "2002-01-01",
                        title = "Star Wars: Episode II - Attack of the Clones"
                    )
                ) else throw FakeException("Something went wrong!")
            }
        }
    }

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

                return listOf(
                    MovieData(
                        id = 0,
                        posterPath = "somePath0",
                        releaseDate = "1999-01-01",
                        title = "Star Wars: Episode I - The Phantom Menace"
                    ),
                    MovieData(
                        id = 1,
                        posterPath = "somePath1",
                        releaseDate = "2002-01-01",
                        title = "Star Wars: Episode II - Attack of the Clones"
                    )
                )
            }
        }
    }
}