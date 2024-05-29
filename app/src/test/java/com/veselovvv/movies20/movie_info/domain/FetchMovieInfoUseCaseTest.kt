package com.veselovvv.movies20.movie_info.domain

import com.veselovvv.movies20.core.ErrorType
import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.movie_info.data.MovieInfoData
import com.veselovvv.movies20.movie_info.domain.FakeMovieInfoRepository.Companion.REPOSITORY_FETCH_MOVIE_INFO
import com.veselovvv.movies20.movie_info.domain.FakeMoviesInfoDataToDomainMapper.Companion.MOVIES_INFO_MAP_DOMAIN_FAIL
import com.veselovvv.movies20.movie_info.domain.FakeMoviesInfoDataToDomainMapper.Companion.MOVIES_INFO_MAP_DOMAIN_SUCCESS
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class FetchMovieInfoUseCaseTest {
    private lateinit var order: Order
    private lateinit var repository: FakeMovieInfoRepository
    private lateinit var movieInfoMapper: FakeMovieInfoDataToDomainMapper
    private lateinit var moviesInfoMapper: FakeMoviesInfoDataToDomainMapper
    private lateinit var useCase: FetchMovieInfoUseCase

    @Before
    fun setup() {
        order = Order()
        repository = FakeMovieInfoRepository.Base(order)
        movieInfoMapper = FakeMovieInfoDataToDomainMapper.Base(order)
        moviesInfoMapper = FakeMoviesInfoDataToDomainMapper.Base(order, movieInfoMapper)
        useCase = FetchMovieInfoUseCase.Base(
            repository = repository,
            mapper = moviesInfoMapper
        )
    }

    @Test
    fun test_success() = runBlocking {
        repository.expectSuccess()

        val movieInfoData = MovieInfoData(
            budget = 100000,
            overview = "Some overview here",
            posterPath = "somePath",
            releaseDate = "2002-01-01",
            revenue = 10000L,
            runtime = 90,
            title = "Star Wars: Episode II - Attack of the Clones",
            rating = 4.9
        )

        val expected = MoviesInfoDomain.Success(
            movieInfo = movieInfoData,
            movieInfoMapper = movieInfoMapper
        )
        val actual = useCase.execute(id = 1)
        assertEquals(expected, actual)

        repository.checkCalledCount(1)
        moviesInfoMapper.checkMapSuccessCalledCount(1)
        moviesInfoMapper.checkMapFailCalledCount(0)
        order.check(listOf(REPOSITORY_FETCH_MOVIE_INFO, MOVIES_INFO_MAP_DOMAIN_SUCCESS))
    }

    @Test
    fun test_fail() = runBlocking {
        repository.expectFail(UnknownHostException())

        var expected = MoviesInfoDomain.Fail(errorType = ErrorType.NO_CONNECTION)
        var actual = useCase.execute(id = 1)
        assertEquals(expected, actual)

        repository.checkCalledCount(1)
        moviesInfoMapper.checkMapSuccessCalledCount(0)
        moviesInfoMapper.checkMapFailCalledCount(1)
        order.check(listOf(REPOSITORY_FETCH_MOVIE_INFO, MOVIES_INFO_MAP_DOMAIN_FAIL))

        repository.expectFail(Exception())

        expected = MoviesInfoDomain.Fail(errorType = ErrorType.GENERIC_ERROR)
        actual = useCase.execute(id = 1)
        assertEquals(expected, actual)

        repository.checkCalledCount(2)
        moviesInfoMapper.checkMapSuccessCalledCount(0)
        moviesInfoMapper.checkMapFailCalledCount(2)
        order.check(
            listOf(
                REPOSITORY_FETCH_MOVIE_INFO,
                MOVIES_INFO_MAP_DOMAIN_FAIL,
                REPOSITORY_FETCH_MOVIE_INFO,
                MOVIES_INFO_MAP_DOMAIN_FAIL
            )
        )
    }
}