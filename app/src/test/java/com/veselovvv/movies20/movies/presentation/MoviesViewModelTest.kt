package com.veselovvv.movies20.movies.presentation

import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.movies.data.FakeMoviesCloudDataSource
import com.veselovvv.movies20.movies.data.FakeMoviesCloudMapper
import com.veselovvv.movies20.movies.domain.FakeMovieDataToDomainMapper
import com.veselovvv.movies20.movies.domain.FakeMoviesDataToDomainMapper
import com.veselovvv.movies20.movies.domain.FakeMoviesDataToDomainMapper.Companion.MOVIES_MAP_DOMAIN
import com.veselovvv.movies20.movies.domain.FakeMoviesRepository
import com.veselovvv.movies20.movies.domain.FakeMoviesRepository.Companion.REPOSITORY_FETCH_MOVIES
import com.veselovvv.movies20.movies.presentation.FakeFetchMoviesUseCase.Companion.FETCH_MOVIES_EXECUTE
import com.veselovvv.movies20.movies.presentation.FakeMovieCache.Base.Companion.SAVE_MOVIE_INFO
import com.veselovvv.movies20.movies.presentation.FakeMoviesCommunication.Companion.MOVIES_COMMUNICATION_MAP
import com.veselovvv.movies20.movies.presentation.FakeMoviesDomainToUiMapper.Companion.MOVIES_MAP_UI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class MoviesViewModelTest {
    private lateinit var order: Order
    private lateinit var cloudDataSource: FakeMoviesCloudDataSource
    private lateinit var cloudMapper: FakeMoviesCloudMapper
    private lateinit var repository: FakeMoviesRepository
    private lateinit var movieDataToDomainMapper: FakeMovieDataToDomainMapper
    private lateinit var moviesDataToDomainMapper: FakeMoviesDataToDomainMapper
    private lateinit var fetchMoviesUseCase: FakeFetchMoviesUseCase
    private lateinit var communication: FakeMoviesCommunication
    private lateinit var moviesDomainToUiMapper: FakeMoviesDomainToUiMapper
    private lateinit var movieCache: FakeMovieCache
    private lateinit var viewModel: MoviesViewModel

    @Before
    fun setup() {
        order = Order()
        cloudDataSource = FakeMoviesCloudDataSource.Base(order)
        cloudMapper = FakeMoviesCloudMapper.Base(order)
        movieDataToDomainMapper = FakeMovieDataToDomainMapper.Base(order)
        repository = FakeMoviesRepository.Base(order, cloudDataSource, cloudMapper)
        moviesDataToDomainMapper = FakeMoviesDataToDomainMapper.Base(order, movieDataToDomainMapper)
        fetchMoviesUseCase = FakeFetchMoviesUseCase.Base(order, repository, moviesDataToDomainMapper)
        communication = FakeMoviesCommunication.Base(order)
        moviesDomainToUiMapper = FakeMoviesDomainToUiMapper.Base(order, BaseMovieDomainToUiMapper())
        movieCache = FakeMovieCache.Base(order)
        viewModel = MoviesViewModel(
            fetchMoviesUseCase = fetchMoviesUseCase,
            communication = communication,
            dispatcher = Dispatchers.Unconfined,
            dispatcherMain = Dispatchers.Unconfined,
            mapper = moviesDomainToUiMapper,
            movieCache = movieCache
        )
    }

    @Test
    fun test_fetch_movies() = runBlocking {
        viewModel.fetchMovies()

        fetchMoviesUseCase.checkCalledCount(1)
        repository.checkFetchMoviesCalledCount(1)
        moviesDataToDomainMapper.checkMapCalledCount(1)
        moviesDomainToUiMapper.checkMapCalledCount(1)
        communication.checkMapCalledCount(1)
        order.check(
            listOf(
                FETCH_MOVIES_EXECUTE,
                REPOSITORY_FETCH_MOVIES,
                MOVIES_MAP_DOMAIN,
                MOVIES_MAP_UI,
                MOVIES_COMMUNICATION_MAP
            )
        )
    }

    @Test
    fun test_search_movies() = runBlocking {
        viewModel.searchMovies(query = "Star Wars: Episode II")

        fetchMoviesUseCase.checkCalledCount(1)
        repository.checkFetchMoviesCalledCount(1)
        moviesDataToDomainMapper.checkMapCalledCount(1)
        moviesDomainToUiMapper.checkMapCalledCount(1)
        communication.checkMapCalledCount(1)
        order.check(
            listOf(
                FETCH_MOVIES_EXECUTE,
                REPOSITORY_FETCH_MOVIES,
                MOVIES_MAP_DOMAIN,
                MOVIES_MAP_UI,
                MOVIES_COMMUNICATION_MAP
            )
        )
    }

    @Test
    fun test_save_movie_info() {
        viewModel.saveMovieInfo(
            id = 1,
            posterPath = "somePath1",
            releaseDate = "2021-01-09",
            title = "Star Wars: Episode II - Attack of the Clones"
        )

        movieCache.checkSaveCalledCount(1)
        order.check(listOf(SAVE_MOVIE_INFO))
    }
}
