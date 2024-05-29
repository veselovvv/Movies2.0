package com.veselovvv.movies20.movies.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.veselovvv.movies20.core.di.CoreDomainModule
import com.veselovvv.movies20.movies.domain.FetchMoviesUseCase
import com.veselovvv.movies20.movies.domain.MoviesDomainToUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val fetchMoviesUseCase: FetchMoviesUseCase,
    private val communication: MoviesCommunication,
    @CoreDomainModule.IODispatcher private val dispatcher: CoroutineDispatcher,
    @CoreDomainModule.MainDispatcher private val dispatcherMain: CoroutineDispatcher,
    private val mapper: MoviesDomainToUiMapper,
    private val movieCache: MovieCache.Save
) : ViewModel() {
    fun fetchMovies() {
        viewModelScope.launch(dispatcher) {
            val moviesDomainList = fetchMoviesUseCase.execute().cachedIn(viewModelScope)
            val moviesUiList = mapper.map(moviesDomainList)

            withContext(dispatcherMain) {
                communication.map(moviesUiList)
            }
        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch(dispatcher) {
            val moviesDomainList = fetchMoviesUseCase.execute().cachedIn(viewModelScope)
            val moviesUiList = mapper.map(moviesDomainList)
            val searchResultMoviesUiList = moviesUiList.map { pagingData ->
                pagingData.filter { movie ->
                    movie.titleContainsWith(query)
                }
            }
            withContext(dispatcherMain) {
                communication.map(searchResultMoviesUiList)
            }
        }
    }

    fun saveMovieInfo(id: Int, posterPath: String, releaseDate: String, title: String) =
        movieCache.save(MovieParameters(id, posterPath, releaseDate, title))

    fun observe(owner: LifecycleOwner, observer: Observer<Flow<PagingData<MovieUi>>>) =
        communication.observe(owner, observer)
}