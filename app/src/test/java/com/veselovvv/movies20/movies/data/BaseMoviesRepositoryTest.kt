package com.veselovvv.movies20.movies.data

import androidx.paging.map
import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.movies.data.FakeMoviesCloudDataSource.Companion.FETCH_MOVIES
import com.veselovvv.movies20.movies.data.FakeMoviesCloudMapper.Companion.MOVIES_CLOUD_MAP
import com.veselovvv.movies20.movies.data.FakeToMovieMapper.Companion.TO_MOVIE_MAP
import kotlinx.coroutines.flow.map
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
    fun test_fetch_movies() = runBlocking {
        val expectedMovieCloudList = cloudDataSource.fetchMovies(1).getMoviesList()
        val expectedMovieDataList = cloudMapper.map(expectedMovieCloudList)

        expectedMovieDataList.forEach { expectedMovieData ->
            repository.fetchMovies().map { pagingData ->
                pagingData.map { actualMovieData ->
                    assertEquals(expectedMovieData, actualMovieData)
                }
            }
        }

        cloudDataSource.checkCalledCount(1)
        cloudMapper.checkMapCalledCount(1)
        order.check(listOf(FETCH_MOVIES, MOVIES_CLOUD_MAP, TO_MOVIE_MAP, TO_MOVIE_MAP))
    }
}