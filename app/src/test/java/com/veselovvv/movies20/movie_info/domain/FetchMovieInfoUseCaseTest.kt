package com.veselovvv.movies20.movie_info.domain

import com.veselovvv.movies20.core.ErrorType
import com.veselovvv.movies20.core.Order
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
    private lateinit var mapper: FakeMoviesInfoDataToDomainMapper
    private lateinit var movieInfoMapper: FakeMovieInfoDataToDomainMapper
    private lateinit var useCase: FetchMovieInfoUseCase

    @Before
    fun setup() {
        order = Order()
        repository = FakeMovieInfoRepository.Base(order)
        mapper = FakeMoviesInfoDataToDomainMapper.Base(order)
        movieInfoMapper = FakeMovieInfoDataToDomainMapper.Base(order)
        useCase = FetchMovieInfoUseCase.Base(
            repository = repository,
            mapper = mapper
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
        mapper.checkMapSuccessCalledCount(1)
        mapper.checkMapFailCalledCount(0)
        movieInfoMapper.checkMapCalledCount(0)
        order.check(listOf(REPOSITORY_FETCH_MOVIE_INFO, MOVIES_INFO_MAP_DOMAIN_SUCCESS))
    }

    @Test
    fun test_fail() = runBlocking {
        repository.expectFail(UnknownHostException())

        var expected = MoviesInfoDomain.Fail(errorType = ErrorType.NO_CONNECTION)
        var actual = useCase.execute(id = 1)
        assertEquals(expected, actual)

        repository.checkCalledCount(1)
        mapper.checkMapSuccessCalledCount(0)
        mapper.checkMapFailCalledCount(1)
        movieInfoMapper.checkMapCalledCount(0)
        order.check(listOf(REPOSITORY_FETCH_MOVIE_INFO, MOVIES_INFO_MAP_DOMAIN_FAIL))

        repository.expectFail(Exception())

        expected = MoviesInfoDomain.Fail(errorType = ErrorType.GENERIC_ERROR)
        actual = useCase.execute(id = 1)
        assertEquals(expected, actual)

        repository.checkCalledCount(1)
        mapper.checkMapSuccessCalledCount(0)
        mapper.checkMapFailCalledCount(2)
        movieInfoMapper.checkMapCalledCount(0)
        order.check(
            listOf(
                REPOSITORY_FETCH_MOVIE_INFO,
                MOVIES_INFO_MAP_DOMAIN_FAIL,
                MOVIES_INFO_MAP_DOMAIN_FAIL
            )
        )
    }
}