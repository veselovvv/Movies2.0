package com.veselovvv.movies20.movie_info.domain

import com.veselovvv.movies20.core.ErrorType
import com.veselovvv.movies20.movie_info.data.MovieInfoData
import com.veselovvv.movies20.movie_info.data.MovieInfoDataToDomainMapper
import com.veselovvv.movies20.movie_info.data.MoviesInfoDataToDomainMapper
import retrofit2.HttpException
import java.net.UnknownHostException

class BaseMoviesInfoDataToDomainMapper(
    private val mapper: MovieInfoDataToDomainMapper
) : MoviesInfoDataToDomainMapper {
    override fun map(movieInfo: MovieInfoData) = MoviesInfoDomain.Success(movieInfo, mapper)

    override fun map(exception: Exception) = MoviesInfoDomain.Fail(
        when (exception) {
            is UnknownHostException -> ErrorType.NO_CONNECTION
            is HttpException -> ErrorType.SERVICE_UNAVAILABLE
            else -> ErrorType.GENERIC_ERROR
        }
    )
}