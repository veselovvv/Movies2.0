package com.veselovvv.movies20.movie_info.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veselovvv.movies20.core.di.CoreDomainModule
import com.veselovvv.movies20.movie_info.domain.FetchMovieInfoUseCase
import com.veselovvv.movies20.movie_info.domain.MoviesInfoDomainToUiMapper
import com.veselovvv.movies20.movies.presentation.MovieCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesInfoViewModel @Inject constructor(
    private val fetchMovieInfoUseCase: FetchMovieInfoUseCase,
    private val communication: MoviesInfoCommunication,
    @CoreDomainModule.IODispatcher private val dispatcher: CoroutineDispatcher,
    @CoreDomainModule.MainDispatcher private val dispatcherMain: CoroutineDispatcher,
    private val mapper: MoviesInfoDomainToUiMapper,
    private val movieCache: MovieCache.Read
) : ViewModel() {
    fun fetchMovieInfo(id: Int) {
        communication.map(MovieInfoUi.Progress)
        viewModelScope.launch(dispatcher) {
            val resultDomain = fetchMovieInfoUseCase.execute(id)
            val resultUi = resultDomain.map(mapper)
            withContext(dispatcherMain) {
                resultUi.map(communication)
            }
        }
    }

    fun observe(owner: LifecycleOwner, observer: Observer<MovieInfoUi>) =
        communication.observe(owner, observer)

    fun getMovieId() = movieCache.read().getId()
    fun getMoviePosterPath() = movieCache.read().getPosterPath()
    fun getMovieReleaseDate() = movieCache.read().getReleaseDate()
    fun getMovieTitle() = movieCache.read().getTitle()
}