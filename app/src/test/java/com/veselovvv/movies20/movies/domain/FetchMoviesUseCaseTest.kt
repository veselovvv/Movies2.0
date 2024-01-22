package com.veselovvv.movies20.movies.domain

import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.movies.domain.FakeMoviesDataToDomainMapper.Companion.MOVIES_MAP_DOMAIN_FAIL
import com.veselovvv.movies20.movies.domain.FakeMoviesDataToDomainMapper.Companion.MOVIES_MAP_DOMAIN_SUCCESS
import com.veselovvv.movies20.movies.domain.FakeMoviesRepository.Companion.REPOSITORY_FETCH_MOVIES
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class FetchMoviesUseCaseTest {
    private lateinit var order: Order
    private lateinit var repository: FakeMoviesRepository
    private lateinit var movieMapper: FakeMovieDataToDomainMapper
    private lateinit var moviesMapper: FakeMoviesDataToDomainMapper
    private lateinit var useCase: FetchMoviesUseCase

    @Before
    fun setup() {
        order = Order()
        repository = FakeMoviesRepository.Base(order)
        movieMapper = FakeMovieDataToDomainMapper.Base(order)
        moviesMapper = FakeMoviesDataToDomainMapper.Base(order)
        useCase = FetchMoviesUseCase.Base(repository = repository, mapper = moviesMapper)
    }

    @Test
    fun test_success() = runBlocking {
        repository.expectSuccess()

        val movies = listOf(
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

        val expected = MoviesDomain.Success(movies = movies, movieMapper = movieMapper)
        val actual = useCase.execute()
        assertEquals(expected, actual)
        repository.checkFetchMoviesCalledCount(1)
        repository.checkSearchMoviesCalledCount(0)
        movieMapper.checkMapCalledCount(0)
        moviesMapper.checkMapSuccessCalledCount(1)
        moviesMapper.checkMapFailCalledCount(0)
        order.check(listOf(REPOSITORY_FETCH_MOVIES, MOVIES_MAP_DOMAIN_SUCCESS))
    }

    @Test
    fun test_fail() = runBlocking {
        repository.expectFail(UnknownHostException())

        var expected = MoviesDomain.Fail(error = ErrorType.NO_CONNECTION)
        var actual = useCase.execute()
        assertEquals(expected, actual)
        repository.checkFetchMoviesCalledCount(1)
        repository.checkSearchMoviesCalledCount(0)
        movieMapper.checkMapCalledCount(0)
        moviesMapper.checkMapSuccessCalledCount(0)
        moviesMapper.checkMapFailCalledCount(1)
        order.check(listOf(REPOSITORY_FETCH_MOVIES, MOVIES_MAP_DOMAIN_FAIL))

        repository.expectFail(Exception())

        expected = MoviesDomain.Fail(error = ErrorType.GENERIC_ERROR)
        actual = useCase.execute()
        assertEquals(expected, actual)
        repository.checkFetchMoviesCalledCount(2)
        repository.checkSearchMoviesCalledCount(0)
        movieMapper.checkMapCalledCount(0)
        moviesMapper.checkMapSuccessCalledCount(0)
        moviesMapper.checkMapFailCalledCount(2)
        order.check(listOf(
            REPOSITORY_FETCH_MOVIES, MOVIES_MAP_DOMAIN_FAIL, REPOSITORY_FETCH_MOVIES, MOVIES_MAP_DOMAIN_FAIL
        ))
    }
}