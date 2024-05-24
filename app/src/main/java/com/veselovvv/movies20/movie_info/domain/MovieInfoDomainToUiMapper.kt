package com.veselovvv.movies20.movie_info.domain

interface MovieInfoDomainToUiMapper {
    fun map(
        budget: Int,
        overview: String,
        posterPath: String,
        releaseDate: String,
        revenue: Long,
        runtime: Int,
        title: String,
        rating: Double
    ): MovieInfoUi
}