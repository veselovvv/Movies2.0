package com.veselovvv.movies20.movie_info.data

import com.veselovvv.movies20.movie_info.domain.MovieInfoDomain

interface MovieInfoDataToDomainMapper {
    fun map(
        budget: Int,
        overview: String,
        posterPath: String,
        releaseDate: String,
        revenue: Long,
        runtime: Int,
        title: String,
        rating: Double
    ): MovieInfoDomain
}