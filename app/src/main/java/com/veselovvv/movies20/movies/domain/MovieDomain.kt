package com.veselovvv.movies20.movies.domain

import com.veselovvv.movies20.core.Object
import com.veselovvv.movies20.movies.presentation.MovieUi

data class MovieDomain(
    private val id: Int,
    private val posterPath: String,
    private val releaseDate: String,
    private val title: String
) : Object<MovieUi, MovieDomainToUiMapper> {
    override fun map(mapper: MovieDomainToUiMapper) =
        mapper.map(id, posterPath, releaseDate, title)
}
