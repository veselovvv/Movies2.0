package com.veselovvv.movies20.movies.domain

import com.veselovvv.movies20.core.ErrorType
import com.veselovvv.movies20.movies.presentation.MoviesUi

interface MoviesDomainToUiMapper {
    fun map(movies: List<MovieDomain>): MoviesUi
    fun map(error: ErrorType): MoviesUi
}