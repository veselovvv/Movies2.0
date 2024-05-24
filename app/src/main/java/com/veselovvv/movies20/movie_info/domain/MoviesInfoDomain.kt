package com.veselovvv.movies20.movie_info.domain

import com.veselovvv.movies20.core.ErrorType
import com.veselovvv.movies20.core.Object
import com.veselovvv.movies20.movie_info.data.MovieInfoData
import com.veselovvv.movies20.movie_info.data.MovieInfoDataToDomainMapper

sealed class MoviesInfoDomain : Object<MoviesInfoUi, MoviesInfoDomainToUiMapper> {
    data class Success(
        private val movieInfo: MovieInfoData,
        private val movieInfoMapper: MovieInfoDataToDomainMapper
    ) : MoviesInfoDomain() {
        override fun map(mapper: MoviesInfoDomainToUiMapper) =
            mapper.map(movieInfo.map(movieInfoMapper))
    }

    data class Fail(private val errorType: ErrorType) : MoviesInfoDomain() {
        override fun map(mapper: MoviesInfoDomainToUiMapper) = mapper.map(errorType)
    }
}