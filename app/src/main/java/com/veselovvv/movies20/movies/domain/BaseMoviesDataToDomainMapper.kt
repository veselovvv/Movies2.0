package com.veselovvv.movies20.movies.domain

import com.veselovvv.movies20.core.ErrorType
import com.veselovvv.movies20.movies.data.MovieData
import com.veselovvv.movies20.movies.data.MovieDataToDomainMapper
import com.veselovvv.movies20.movies.data.MoviesDataToDomainMapper
import retrofit2.HttpException
import java.net.UnknownHostException

class BaseMoviesDataToDomainMapper(
    private val movieMapper: MovieDataToDomainMapper
) : MoviesDataToDomainMapper {
    override fun map(movies: List<MovieData>) = MoviesDomain.Success(movies, movieMapper)

    override fun map(exception: Exception) = MoviesDomain.Fail(
        when (exception) {
            is UnknownHostException -> ErrorType.NO_CONNECTION
            is HttpException -> ErrorType.SERVICE_UNAVAILABLE
            else -> ErrorType.GENERIC_ERROR
        }
    )
}