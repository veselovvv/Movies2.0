package com.veselovvv.movies20.movie_info.data

import com.veselovvv.movies20.core.Object
import com.veselovvv.movies20.movie_info.domain.MovieInfoDomain

data class MovieInfoData(
    private val budget: Int,
    private val overview: String,
    private val posterPath: String,
    private val releaseDate: String,
    private val revenue: Long,
    private val runtime: Int,
    private val title: String,
    private val rating: Double
) : Object<MovieInfoDomain, MovieInfoDataToDomainMapper> {
    override fun map(mapper: MovieInfoDataToDomainMapper) =
        mapper.map(budget, overview, posterPath, releaseDate, revenue, runtime, title, rating)
}
