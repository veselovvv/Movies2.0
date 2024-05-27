package com.veselovvv.movies20.movie_info.domain

import com.veselovvv.movies20.movie_info.presentation.MovieInfoUi

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

    fun formatPrice(value: String): String
    fun formatDate(value: String): String
}