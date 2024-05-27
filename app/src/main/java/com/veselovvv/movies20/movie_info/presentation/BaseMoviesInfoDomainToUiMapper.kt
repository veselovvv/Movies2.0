package com.veselovvv.movies20.movie_info.presentation

import com.veselovvv.movies20.R
import com.veselovvv.movies20.core.ErrorType
import com.veselovvv.movies20.core.ResourceProvider
import com.veselovvv.movies20.movie_info.domain.MovieInfoDomain
import com.veselovvv.movies20.movie_info.domain.MovieInfoDomainToUiMapper
import com.veselovvv.movies20.movie_info.domain.MoviesInfoDomainToUiMapper

class BaseMoviesInfoDomainToUiMapper(
    private val resourceProvider: ResourceProvider,
    private val mapper: MovieInfoDomainToUiMapper
) : MoviesInfoDomainToUiMapper {
    override fun map(movieInfo: MovieInfoDomain) = MoviesInfoUi.Success(movieInfo, mapper)

    override fun map(errorType: ErrorType) = MoviesInfoUi.Fail(
        resourceProvider.getString(
            when (errorType) {
                ErrorType.NO_CONNECTION -> R.string.no_connection_message
                ErrorType.SERVICE_UNAVAILABLE -> R.string.service_unavailable_message
                else -> R.string.something_went_wrong
            }
        )
    )
}