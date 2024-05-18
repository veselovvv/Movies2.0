package com.veselovvv.movies20.movies.domain

import androidx.paging.map
import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.movies.data.FakeMoviesCloudDataSource
import com.veselovvv.movies20.movies.data.FakeMoviesCloudMapper
import com.veselovvv.movies20.movies.data.MoviesDataToDomainMapper
import com.veselovvv.movies20.movies.domain.FakeMoviesRepository.Companion.REPOSITORY_FETCH_MOVIES
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BaseMoviesDataToDomainMapperTest {
    private lateinit var order: Order
    private lateinit var movieMapper: FakeMovieDataToDomainMapper
    private lateinit var cloudDataSource: FakeMoviesCloudDataSource
    private lateinit var cloudMapper: FakeMoviesCloudMapper
    private lateinit var repository: FakeMoviesRepository
    private lateinit var mapper: MoviesDataToDomainMapper

    @Before
    fun setup() {
        order = Order()
        cloudDataSource = FakeMoviesCloudDataSource.Base(order)
        cloudMapper = FakeMoviesCloudMapper.Base(order)
        repository = FakeMoviesRepository.Base(order, cloudDataSource, cloudMapper)
        movieMapper = FakeMovieDataToDomainMapper.Base(order)
        mapper = BaseMoviesDataToDomainMapper(movieMapper = movieMapper)
    }

    @Test
    fun test_map() = runBlocking {
        val expectedMovieDataList = repository.fetchMovies()
        val actualMovieDomainList = mapper.map(movies = expectedMovieDataList)

        expectedMovieDataList.map { expectedPagingData ->
            expectedPagingData.map { expectedMovieData ->
                actualMovieDomainList.map { actualPagingData ->
                    actualPagingData.map { actualMovieDomain ->
                        assertEquals(expectedMovieData.map(movieMapper), actualMovieDomain)
                    }
                }
            }
        }

        repository.checkFetchMoviesCalledCount(1)
        movieMapper.checkMapCalledCount(0)
        order.check(listOf(REPOSITORY_FETCH_MOVIES))
    }
}