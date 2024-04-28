package com.veselovvv.movies20.movies.presentation

import com.veselovvv.movies20.R
import com.veselovvv.movies20.core.ErrorType
import com.veselovvv.movies20.core.ResourceProvider
import com.veselovvv.movies20.movies.domain.MovieDomain
import com.veselovvv.movies20.movies.domain.MovieDomainToUiMapper
import com.veselovvv.movies20.movies.domain.MoviesDomainToUiMapper

class BaseMoviesDomainToUiMapper(
    private val resourceProvider: ResourceProvider,
    private val movieMapper: MovieDomainToUiMapper
) : MoviesDomainToUiMapper {
    override fun map(movies: List<MovieDomain>) = MoviesUi.Success(movies, movieMapper)

    override fun map(error: ErrorType) = MoviesUi.Fail(
        resourceProvider.getString(
            when (error) {
                ErrorType.NO_CONNECTION -> R.string.no_connection_message
                ErrorType.SERVICE_UNAVAILABLE -> R.string.service_unavailable_message
                else -> R.string.something_went_wrong
            }
        )
    )
}