package com.veselovvv.movies20.movie_info.domain

import com.veselovvv.movies20.core.ErrorType
import com.veselovvv.movies20.movie_info.presentation.MoviesInfoUi

interface MoviesInfoDomainToUiMapper {
    fun map(movieInfo: MovieInfoDomain): MoviesInfoUi
    fun map(errorType: ErrorType): MoviesInfoUi
}