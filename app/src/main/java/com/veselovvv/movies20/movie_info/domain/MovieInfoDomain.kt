package com.veselovvv.movies20.movie_info.domain

import com.veselovvv.movies20.core.Object
import com.veselovvv.movies20.movie_info.presentation.MovieInfoUi

data class MovieInfoDomain(
    private val budget: Int,
    private val overview: String,
    private val posterPath: String,
    private val releaseDate: String,
    private val revenue: Long,
    private val runtime: Int,
    private val title: String,
    private val rating: Double
) : Object<MovieInfoUi, MovieInfoDomainToUiMapper> {
    override fun map(mapper: MovieInfoDomainToUiMapper) =
        mapper.map(budget, overview, posterPath, releaseDate, revenue, runtime, title, rating)
}
