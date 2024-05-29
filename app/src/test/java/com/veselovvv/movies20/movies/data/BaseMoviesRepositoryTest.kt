package com.veselovvv.movies20.movies.data

import com.veselovvv.movies20.core.Order
import kotlinx.coroutines.runBlocking
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
    fun test_fetch_movies() = runBlocking {
        repository.fetchMovies()

        cloudDataSource.checkCalledCount(0)
        cloudMapper.checkMapCalledCount(0)
        order.check(listOf())
    }
}