package com.veselovvv.movies20.movies.domain

import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.movies.data.FakeMoviesCloudDataSource
import com.veselovvv.movies20.movies.data.FakeMoviesCloudMapper
import com.veselovvv.movies20.movies.domain.FakeMoviesDataToDomainMapper.Companion.MOVIES_MAP_DOMAIN
import com.veselovvv.movies20.movies.domain.FakeMoviesRepository.Companion.REPOSITORY_FETCH_MOVIES
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FetchMoviesUseCaseTest {
    private lateinit var order: Order
    private lateinit var cloudDataSource: FakeMoviesCloudDataSource
    private lateinit var cloudMapper: FakeMoviesCloudMapper
    private lateinit var repository: FakeMoviesRepository
    private lateinit var movieMapper: FakeMovieDataToDomainMapper
    private lateinit var moviesMapper: FakeMoviesDataToDomainMapper
    private lateinit var useCase: FetchMoviesUseCase

    @Before
    fun setup() {
        order = Order()
        cloudDataSource = FakeMoviesCloudDataSource.Base(order)
        cloudMapper = FakeMoviesCloudMapper.Base(order)
        repository = FakeMoviesRepository.Base(order, cloudDataSource, cloudMapper)
        movieMapper = FakeMovieDataToDomainMapper.Base(order)
        moviesMapper = FakeMoviesDataToDomainMapper.Base(order, movieMapper)
        useCase = FetchMoviesUseCase.Base(repository = repository, mapper = moviesMapper)
    }

    @Test
    fun test() = runBlocking {
        useCase.execute()

        repository.checkFetchMoviesCalledCount(1)
        moviesMapper.checkMapCalledCount(1)
        order.check(listOf(REPOSITORY_FETCH_MOVIES, MOVIES_MAP_DOMAIN))
    }
}
