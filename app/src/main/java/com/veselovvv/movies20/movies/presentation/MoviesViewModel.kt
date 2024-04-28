package com.veselovvv.movies20.movies.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veselovvv.movies20.core.di.CoreDomainModule
import com.veselovvv.movies20.movies.domain.FetchMoviesUseCase
import com.veselovvv.movies20.movies.domain.MoviesDomainToUiMapper
import com.veselovvv.movies20.movies.domain.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val fetchMoviesUseCase: FetchMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val communication: MoviesCommunication,
    @CoreDomainModule.IODispatcher private val dispatcher: CoroutineDispatcher,
    @CoreDomainModule.MainDispatcher private val dispatcherMain: CoroutineDispatcher,
    private val mapper: MoviesDomainToUiMapper,
    private val movieCache: MovieCache.Save
) : ViewModel() {
    fun fetchMovies() {
        communication.map(listOf(MovieUi.Progress))
        viewModelScope.launch(dispatcher) {
            val resultDomain = fetchMoviesUseCase.execute()
            val resultUi = resultDomain.map(mapper)
            withContext(dispatcherMain) {
                resultUi.map(communication)
            }
        }
    }

    fun searchMovies(query: String) {
        communication.map(listOf(MovieUi.Progress))
        viewModelScope.launch(dispatcher) {
            val resultDomain = searchMoviesUseCase.execute(query)
            val resultUi = resultDomain.map(mapper)
            withContext(dispatcherMain) {
                resultUi.map(communication)
            }
        }
    }

    fun saveMovieInfo(id: Int, posterPath: String, releaseDate: String, title: String) =
        movieCache.save(MovieParameters(id, posterPath, releaseDate, title))

    fun observe(owner: LifecycleOwner, observer: Observer<List<MovieUi>>) =
        communication.observe(owner, observer)
}