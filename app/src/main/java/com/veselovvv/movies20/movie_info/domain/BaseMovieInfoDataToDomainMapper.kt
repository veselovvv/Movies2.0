package com.veselovvv.movies20.movie_info.domain

import com.veselovvv.movies20.movie_info.data.MovieInfoDataToDomainMapper

class BaseMovieInfoDataToDomainMapper : MovieInfoDataToDomainMapper {
    override fun map(
        budget: Int,
        overview: String,
        posterPath: String,
        releaseDate: String,
        revenue: Long,
        runtime: Int,
        title: String,
        rating: Double
    ) = MovieInfoDomain(
        budget, overview, posterPath, releaseDate, revenue, runtime, title, rating
    )
}