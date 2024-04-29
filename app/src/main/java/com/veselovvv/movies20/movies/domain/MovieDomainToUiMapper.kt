package com.veselovvv.movies20.movies.domain

import com.veselovvv.movies20.movies.presentation.MovieUi

interface MovieDomainToUiMapper {
    fun map(
        id: Int,
        posterPath: String,
        releaseDate: String,
        title: String
    ): MovieUi
}