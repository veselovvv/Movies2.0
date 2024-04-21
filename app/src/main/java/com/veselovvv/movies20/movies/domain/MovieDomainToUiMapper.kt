package com.veselovvv.movies20.movies.domain

interface MovieDomainToUiMapper {
    fun map(
        id: Int,
        posterPath: String,
        releaseDate: String,
        title: String
    ): MovieUi
}