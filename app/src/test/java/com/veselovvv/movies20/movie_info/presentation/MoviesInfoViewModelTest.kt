package com.veselovvv.movies20.movie_info.presentation

import com.veselovvv.movies20.core.ErrorType
import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.movie_info.presentation.BaseMoviesInfoDomainToUiMapperTest.FakeResourceProvider.Base.Companion.GENERIC_ERROR_MESSAGE
import com.veselovvv.movies20.movie_info.presentation.BaseMoviesInfoDomainToUiMapperTest.FakeResourceProvider.Base.Companion.NO_CONNECTION_MESSAGE
import com.veselovvv.movies20.movie_info.presentation.BaseMoviesInfoDomainToUiMapperTest.FakeResourceProvider.Base.Companion.SERVICE_UNAVAILABLE_MESSAGE
import com.veselovvv.movies20.movie_info.presentation.FakeMoviesInfoCommunication.Companion.MOVIES_INFO_COMMUNICATION_MAP
import com.veselovvv.movies20.movie_info.presentation.FakeMoviesInfoDomainToUiMapper.Companion.MOVIES_INFO_MAP_UI_FAIL
import com.veselovvv.movies20.movie_info.presentation.FakeMoviesInfoDomainToUiMapper.Companion.MOVIES_INFO_MAP_UI_SUCCESS
import com.veselovvv.movies20.movie_info.presentation.MoviesInfoViewModelTest.FakeFetchMovieInfoUseCase.Companion.FETCH_MOVIE_INFO_EXECUTE
import com.veselovvv.movies20.movies.presentation.FakeMovieCache
import com.veselovvv.movies20.movies.presentation.FakeMovieCache.Base.Companion.READ_MOVIE_INFO
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class MoviesInfoViewModelTest {
    private lateinit var order: Order
    private lateinit var fetchMovieInfoUseCase: FakeFetchMovieInfoUseCase
    private lateinit var communication: FakeMoviesInfoCommunication
    private lateinit var mapper: FakeMoviesInfoDomainToUiMapper
    private lateinit var movieCache: FakeMovieCache
    private lateinit var viewModel: MoviesInfoViewModel

    @Before
    fun setup() {
        order = Order()
        fetchMovieInfoUseCase = FakeFetchMovieInfoUseCase.Base(order)
        communication = FakeMoviesInfoCommunication.Base(order)
        mapper = FakeMoviesInfoDomainToUiMapper.Base(order)
        movieCache = FakeMovieCache.Base(order)
        viewModel = MoviesInfoViewModel(
            fetchMovieInfoUseCase = fetchMovieInfoUseCase,
            communication = communication,
            dispatcher = Dispatchers.Unconfined,
            dispatcherMain = Dispatchers.Unconfined,
            mapper = mapper,
            movieCache = movieCache
        )
    }

    @Test
    fun test_fetch_movie_info_success() = runBlocking {
        fetchMovieInfoUseCase.expectSuccess()

        viewModel.fetchMovieInfo(id = 1)

        val expected = MovieInfoUi.Base(
            budget = "$100.000",
            overview = "Some overview here",
            posterPath = "somePath",
            releaseDate = "01.01.2002",
            revenue = "$10.000",
            runtime = "90",
            title = "Star Wars: Episode II - Attack of the Clones",
            rating = "4.9"
        )
        communication.checkMovieInfo(expected)
        communication.checkMapCalledCount(2)
        fetchMovieInfoUseCase.checkCalledCount(1)
        mapper.checkMapSuccessCalledCount(1)
        mapper.checkMapFailCalledCount(0)
        movieCache.checkSaveCalledCount(0)
        movieCache.checkReadCalledCount(0)
        order.check(
            listOf(
                MOVIES_INFO_COMMUNICATION_MAP,
                FETCH_MOVIE_INFO_EXECUTE,
                MOVIES_INFO_MAP_UI_SUCCESS,
                MOVIES_INFO_COMMUNICATION_MAP
            )
        )
    }

    @Test
    fun test_fetch_movie_info_fail() = runBlocking {
        fetchMovieInfoUseCase.expectFail(ErrorType.NO_CONNECTION)

        viewModel.fetchMovieInfo(id = 1)

        var expected = MovieInfoUi.Fail(message = NO_CONNECTION_MESSAGE)
        communication.checkMovieInfo(expected)
        communication.checkMapCalledCount(2)
        fetchMovieInfoUseCase.checkCalledCount(1)
        mapper.checkMapSuccessCalledCount(0)
        mapper.checkMapFailCalledCount(1)
        movieCache.checkSaveCalledCount(0)
        movieCache.checkReadCalledCount(0)
        order.check(
            listOf(
                MOVIES_INFO_COMMUNICATION_MAP,
                FETCH_MOVIE_INFO_EXECUTE,
                MOVIES_INFO_MAP_UI_FAIL,
                MOVIES_INFO_COMMUNICATION_MAP
            )
        )

        fetchMovieInfoUseCase.expectFail(ErrorType.SERVICE_UNAVAILABLE)

        viewModel.fetchMovieInfo(id = 1)

        expected = MovieInfoUi.Fail(message = SERVICE_UNAVAILABLE_MESSAGE)
        communication.checkMovieInfo(expected)
        communication.checkMapCalledCount(4)
        fetchMovieInfoUseCase.checkCalledCount(2)
        mapper.checkMapSuccessCalledCount(0)
        mapper.checkMapFailCalledCount(2)
        movieCache.checkSaveCalledCount(0)
        movieCache.checkReadCalledCount(0)
        order.check(
            listOf(
                MOVIES_INFO_COMMUNICATION_MAP,
                FETCH_MOVIE_INFO_EXECUTE,
                MOVIES_INFO_MAP_UI_FAIL,
                MOVIES_INFO_COMMUNICATION_MAP,
                MOVIES_INFO_COMMUNICATION_MAP,
                FETCH_MOVIE_INFO_EXECUTE,
                MOVIES_INFO_MAP_UI_FAIL,
                MOVIES_INFO_COMMUNICATION_MAP
            )
        )

        fetchMovieInfoUseCase.expectFail(ErrorType.GENERIC_ERROR)

        viewModel.fetchMovieInfo(id = 1)

        expected = MovieInfoUi.Fail(message = GENERIC_ERROR_MESSAGE)
        communication.checkMovieInfo(expected)
        communication.checkMapCalledCount(6)
        fetchMovieInfoUseCase.checkCalledCount(3)
        mapper.checkMapSuccessCalledCount(0)
        mapper.checkMapFailCalledCount(3)
        movieCache.checkSaveCalledCount(0)
        movieCache.checkReadCalledCount(0)
        order.check(
            listOf(
                MOVIES_INFO_COMMUNICATION_MAP,
                FETCH_MOVIE_INFO_EXECUTE,
                MOVIES_INFO_MAP_UI_FAIL,
                MOVIES_INFO_COMMUNICATION_MAP,
                MOVIES_INFO_COMMUNICATION_MAP,
                FETCH_MOVIE_INFO_EXECUTE,
                MOVIES_INFO_MAP_UI_FAIL,
                MOVIES_INFO_COMMUNICATION_MAP,
                MOVIES_INFO_COMMUNICATION_MAP,
                FETCH_MOVIE_INFO_EXECUTE,
                MOVIES_INFO_MAP_UI_FAIL,
                MOVIES_INFO_COMMUNICATION_MAP
            )
        )
    }

    @Test
    fun test_read_movie_info_id() {
        val expected = 1
        val actual = viewModel.getMovieId()
        assertEquals(expected, actual)

        movieCache.checkSaveCalledCount(0)
        movieCache.checkReadCalledCount(1)
        order.check(listOf(READ_MOVIE_INFO))
    }

    @Test
    fun test_read_movie_poster_path() {
        val expected = "somePath1"
        val actual = viewModel.getMoviePosterPath()
        assertEquals(expected, actual)

        movieCache.checkSaveCalledCount(0)
        movieCache.checkReadCalledCount(1)
        order.check(listOf(READ_MOVIE_INFO))
    }

    @Test
    fun test_read_movie_release_date() {
        val expected = "2021-01-09"
        val actual = viewModel.getMovieReleaseDate()
        assertEquals(expected, actual)

        movieCache.checkSaveCalledCount(0)
        movieCache.checkReadCalledCount(1)
        order.check(listOf(READ_MOVIE_INFO))
    }

    @Test
    fun test_read_movie_title() {
        val expected = "Star Wars: Episode II - Attack of the Clones"
        val actual = viewModel.getMovieTitle()
        assertEquals(expected, actual)

        movieCache.checkSaveCalledCount(0)
        movieCache.checkReadCalledCount(1)
        order.check(listOf(READ_MOVIE_INFO))
    }

    interface FakeFetchMovieInfoUseCase : FetchMovieInfoUseCase {
        companion object {
            const val FETCH_MOVIE_INFO_EXECUTE = "FakeFetchMovieInfoUseCase#execute"
        }

        fun expectSuccess()
        fun expectFail(errorType: ErrorType)
        fun checkCalledCount(count: Int)

        class Base(private val order: Order) : FakeFetchMovieInfoUseCase {
            private var error: ErrorType? = null
            private var calledCount = 0

            override fun expectSuccess() {
                error = null
            }

            override fun expectFail(errorType: ErrorType) {
                error = errorType
            }

            override fun checkCalledCount(count: Int) {
                assertEquals(count, calledCount)
            }

            override suspend fun execute(id: Int): MoviesInfoDomain {
                calledCount++
                order.add(FETCH_MOVIE_INFO_EXECUTE)

                return if (error == null) MoviesInfoDomain.Success(
                    movieInfo = MovieInfoData(
                        budget = 100000,
                        overview = "Some overview here",
                        posterPath = "somePath",
                        releaseDate = "2002-01-01",
                        revenue = 10000L,
                        runtime = 90,
                        title = "Star Wars: Episode II - Attack of the Clones",
                        rating = 4.9
                    ),
                    movieInfoMapper = BaseMovieInfoDataToDomainMapper()
                ) else MoviesInfoDomain.Fail(errorType = error)
            }
        }
    }
}