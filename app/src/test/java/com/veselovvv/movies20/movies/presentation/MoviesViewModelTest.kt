package com.veselovvv.movies20.movies.presentation

import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.movies.presentation.FakeMovieCache.Base.Companion.SAVE_MOVIE_INFO
import com.veselovvv.movies20.movies.presentation.FakeMoviesCommunication.Companion.MOVIES_COMMUNICATION_MAP
import com.veselovvv.movies20.movies.presentation.FakeMoviesDomainToUiMapper.Companion.MOVIES_MAP_UI_FAIL
import com.veselovvv.movies20.movies.presentation.FakeMoviesDomainToUiMapper.Companion.MOVIES_MAP_UI_SUCCESS
import com.veselovvv.movies20.movies.presentation.MoviesViewModelTest.FakeFetchMoviesUseCase.Companion.FETCH_MOVIES_EXECUTE
import com.veselovvv.movies20.movies.presentation.MoviesViewModelTest.FakeSearchMoviesUseCase.Companion.SEARCH_MOVIES_EXECUTE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MoviesViewModelTest {
    private lateinit var order: Order
    private lateinit var fetchMoviesUseCase: FakeFetchMoviesUseCase
    private lateinit var searchMoviesUseCase: FakeSearchMoviesUseCase
    private lateinit var communication: FakeMoviesCommunication
    private lateinit var moviesDomainToUiMapper: FakeMoviesDomainToUiMapper
    private lateinit var movieCache: FakeMovieCache
    private lateinit var viewModel: MoviesViewModel

    @Before
    fun setup() {
        order = Order()
        fetchMoviesUseCase = FakeFetchMoviesUseCase.Base(order)
        searchMoviesUseCase = FakeSearchMoviesUseCase.Base(order)
        communication = FakeMoviesCommunication.Base(order)
        moviesDomainToUiMapper = FakeMoviesDomainToUiMapper.Base(order)
        movieCache = FakeMovieCache.Base(order)
        viewModel = MoviesViewModel(
            fetchMoviesUseCase = fetchMoviesUseCase,
            searchMoviesUseCase = searchMoviesUseCase,
            communication = communication,
            dispatcher = Dispatchers.Unconfined,
            dispatcherMain = Dispatchers.Unconfined,
            mapper = moviesDomainToUiMapper,
            movieCache = movieCache
        )
    }

    @Test
    fun test_fetch_movies_success() = runBlocking {
        fetchMoviesUseCase.expectSuccess()
        searchMoviesUseCase.expectSuccess()
        searchMoviesUseCase.expectListIsNotEmpty()

        viewModel.fetchMovies()

        communication.checkList(
            listOf<MovieUi>(
                MovieUi.Base(
                    id = 0,
                    posterPath = "somePath0",
                    releaseDate = "1999",
                    title = "Star Wars: Episode I - The Phantom Menace"
                ),
                MovieUi.Base(
                    id = 1,
                    posterPath = "somePath1",
                    releaseDate = "2002",
                    title = "Star Wars: Episode II - Attack of the Clones"
                )
            )
        )
        communication.checkMapCalledCount(2)
        fetchMoviesUseCase.checkCalledCount(1)
        searchMoviesUseCase.checkCalledCount(0)
        moviesDomainToUiMapper.checkSuccessMapCalledCount(1)
        moviesDomainToUiMapper.checkFailMapCalledCount(0)
        order.check(
            listOf(
                MOVIES_COMMUNICATION_MAP,
                FETCH_MOVIES_EXECUTE,
                MOVIES_MAP_UI_SUCCESS,
                MOVIES_COMMUNICATION_MAP
            )
        )
    }

    @Test
    fun test_fetch_movies_fail() = runBlocking {
        fetchMoviesUseCase.expectFail()
        searchMoviesUseCase.expectSuccess()
        searchMoviesUseCase.expectListIsNotEmpty()

        viewModel.fetchMovies()

        communication.checkList(
            listOf<MovieUi>(MovieUi.Fail(errorMessage = GENERIC_ERROR_MESSAGE))
        )
        communication.checkMapCalledCount(2)
        fetchMoviesUseCase.checkCalledCount(1)
        searchMoviesUseCase.checkCalledCount(0)
        moviesDomainToUiMapper.checkSuccessMapCalledCount(0)
        moviesDomainToUiMapper.checkFailMapCalledCount(1)
        order.check(
            listOf(
                MOVIES_COMMUNICATION_MAP,
                FETCH_MOVIES_EXECUTE,
                MOVIES_MAP_UI_FAIL,
                MOVIES_COMMUNICATION_MAP
            )
        )
    }

    @Test
    fun test_search_movies_success() = runBlocking {
        fetchMoviesUseCase.expectSuccess()
        searchMoviesUseCase.expectSuccess()
        searchMoviesUseCase.expectListIsNotEmpty()

        viewModel.searchMovies(query = "Star Wars: Episode II")

        communication.checkList(
            listOf<MovieUi>(
                MovieUi.Base(
                    id = 1,
                    posterPath = "somePath1",
                    releaseDate = "2002",
                    title = "Star Wars: Episode II - Attack of the Clones"
                )
            )
        )
        communication.checkMapCalledCount(2)
        fetchMoviesUseCase.checkCalledCount(0)
        searchMoviesUseCase.checkCalledCount(1)
        moviesDomainToUiMapper.checkSuccessMapCalledCount(1)
        moviesDomainToUiMapper.checkFailMapCalledCount(0)
        order.check(
            listOf(
                MOVIES_COMMUNICATION_MAP,
                SEARCH_MOVIES_EXECUTE,
                MOVIES_MAP_UI_SUCCESS,
                MOVIES_COMMUNICATION_MAP
            )
        )

        searchMoviesUseCase.expectListIsEmpty()

        viewModel.searchMovies(query = "Element that does not exist")

        communication.checkList(listOf<MovieUi>(MovieUi.NoResults))
        communication.checkMapCalledCount(4)
        fetchMoviesUseCase.checkCalledCount(0)
        searchMoviesUseCase.checkCalledCount(2)
        moviesDomainToUiMapper.checkSuccessMapCalledCount(2)
        moviesDomainToUiMapper.checkFailMapCalledCount(0)
        order.check(
            listOf(
                MOVIES_COMMUNICATION_MAP,
                SEARCH_MOVIES_EXECUTE,
                MOVIES_MAP_UI_SUCCESS,
                MOVIES_COMMUNICATION_MAP,
                MOVIES_COMMUNICATION_MAP,
                SEARCH_MOVIES_EXECUTE,
                MOVIES_MAP_UI_SUCCESS,
                MOVIES_COMMUNICATION_MAP
            )
        )
    }

    @Test
    fun test_search_movies_fail() = runBlocking {
        fetchMoviesUseCase.expectFail()
        searchMoviesUseCase.expectFail()
        searchMoviesUseCase.expectListIsNotEmpty()

        viewModel.searchMovies(query = "Some query")

        communication.checkList(
            listOf<MovieUi>(MovieUi.Fail(errorMessage = GENERIC_ERROR_MESSAGE))
        )
        communication.checkMapCalledCount(2)
        fetchMoviesUseCase.checkCalledCount(0)
        searchMoviesUseCase.checkCalledCount(1)
        moviesDomainToUiMapper.checkSuccessMapCalledCount(0)
        moviesDomainToUiMapper.checkFailMapCalledCount(1)
        order.check(
            listOf(
                MOVIES_COMMUNICATION_MAP,
                SEARCH_MOVIES_EXECUTE,
                MOVIES_MAP_UI_FAIL,
                MOVIES_COMMUNICATION_MAP
            )
        )
    }

    @Test
    fun test_save_movie_info() {
        viewModel.saveMovieInfo(
            id = 1,
            posterPath = "somePath1",
            title = "Star Wars: Episode II - Attack of the Clones"
        )

        movieCache.checkSaveCalledCount(1)
        communication.checkMapCalledCount(0)
        fetchMoviesUseCase.checkCalledCount(0)
        searchMoviesUseCase.checkCalledCount(0)
        moviesDomainToUiMapper.checkSuccessMapCalledCount(0)
        moviesDomainToUiMapper.checkFailMapCalledCount(0)
        order.check(listOf(SAVE_MOVIE_INFO))
    }

    interface FakeFetchMoviesUseCase : FetchMoviesUseCase {
        companion object {
            const val FETCH_MOVIES_EXECUTE = "FakeFetchMoviesUseCase#execute"
        }

        fun expectSuccess()
        fun expectFail()
        fun checkCalledCount(count: Int)

        class Base(private val order: Order) : FakeFetchMoviesUseCase {
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

            override suspend fun execute(): MoviesDomain {
                calledCount++
                order.add(FETCH_MOVIES_EXECUTE)

                return if (success)
                    MoviesDomain.Success(
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
                        ),
                        movieMapper = BaseMovieDataToDomainMapper()
                    )
                else MoviesDomain.Fail(error = ErrorType.GENERIC_ERROR)
            }
        }
    }

    interface FakeSearchMoviesUseCase : SearchMoviesUseCase {
        companion object {
            const val SEARCH_MOVIES_EXECUTE = "FakeSearchMoviesUseCase#execute"
        }

        fun expectSuccess()
        fun expectFail()
        fun expectListIsEmpty()
        fun expectListIsNotEmpty()
        fun checkCalledCount(count: Int)

        class Base(private val order: Order) : FakeSearchMoviesUseCase {
            private var success = true
            private var calledCount = 0
            private var isListEmpty = false

            override fun expectSuccess() {
                success = true
            }

            override fun expectFail() {
                success = false
            }

            override fun expectListIsEmpty() {
                isListEmpty = true
            }

            override fun expectListIsNotEmpty() {
                isListEmpty = false
            }

            override fun checkCalledCount(count: Int) {
                assertEquals(count, calledCount)
            }

            override suspend fun execute(query: String): MoviesDomain {
                calledCount++
                order.add(SEARCH_MOVIES_EXECUTE)

                return if (success) {
                    if (isListEmpty)
                        MoviesDomain.Success(
                            movies = listOf(),
                            movieMapper = BaseMovieDataToDomainMapper()
                        )
                    else
                        MoviesDomain.Success(
                            movies = listOf(
                                MovieData(
                                    id = 1,
                                    posterPath = "somePath1",
                                    releaseDate = "2002-01-01",
                                    title = "Star Wars: Episode II - Attack of the Clones"
                                )
                            ),
                            movieMapper = BaseMovieDataToDomainMapper()
                        )
                } else MoviesDomain.Fail(error = ErrorType.GENERIC_ERROR)
            }
        }
    }

    companion object {
        private const val GENERIC_ERROR_MESSAGE = "Something went wrong. Please try again!"
    }
}