package com.veselovvv.movies20.movie_info.data

import com.veselovvv.movies20.core.Object
import com.veselovvv.movies20.movie_info.domain.MoviesInfoDomain

sealed class MoviesInfoData : Object<MoviesInfoDomain, MoviesInfoDataToDomainMapper> {
    data class Success(private val movieInfo: MovieInfoData) : MoviesInfoData() {
        override fun map(mapper: MoviesInfoDataToDomainMapper) = mapper.map(movieInfo)
    }

    data class Fail(private val exception: Exception) : MoviesInfoData() {
        override fun map(mapper: MoviesInfoDataToDomainMapper) = mapper.map(exception)
    }
}